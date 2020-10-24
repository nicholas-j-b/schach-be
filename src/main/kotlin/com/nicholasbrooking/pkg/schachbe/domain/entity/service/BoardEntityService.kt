package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameStartingPosition
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.game.CreateGameDto
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameUserDto
import com.nicholasbrooking.pkg.schachbe.domain.repository.BoardStateRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameStartingPositionRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameUserRepository
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidState
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiEnum
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalEnum
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob


@ExperimentalStdlibApi
@Service
class BoardEntityService(
        private val boardStateRepository: BoardStateRepository
) {
    fun boardStateDtoToEntity(boardStateDto: BoardStateDto): BoardState {
        val boardState = BoardState(
                whiteStatusDto = boardStateDto.whiteStatus.toDatabaseBlob(),
                blackStatusDto = boardStateDto.blackStatus.toDatabaseBlob(),
                turn = boardStateDto.turn.toInternalEnum(),
                history = SerialBlob(boardStateDto.history.flatMap { it.toDatabaseBlob() }.toByteArray())
        )
        return boardStateRepository.save(boardState)
    }

//    fun boardStateToDto(boardState: BoardState): BoardStateDto {
//        return BoardStateDto()
//                .whiteStatus(bytesToColourStatusDto(boardState.whiteStatusDto))
//                .blackStatus(bytesToColourStatusDto(boardState.blackStatusDto))
//                .turn(boardState.turn.toApiEnum())
//                .history(bytesToHistory(boardState.history))
//    }

    private fun bytesToHistory(blob: List<Byte>): List<MoveCollectionDto> {
        var index = 0
        val moveCollectionDtos = mutableListOf<MoveCollectionDto>()
        while(true) {
            val numMoves = blob[index]
            index += 1
            val moveDtos = mutableListOf<MoveDto>()
            for (i in 0 until numMoves) {
                val fromX = blob[index]
                index += 1
                val fromY = blob[index]
                index += 1
                val toX = blob[index]
                index += 1
                val toY = blob[index]
                index += 1
                val takenX = blob[index]
                index += 1
                val takenY = blob[index]
                index += 1
                val promoteBytes = mutableListOf<Byte>()
                while (true) {
                    val char = promoteBytes[index]
                    if (char == 0.toByte()) {
                        break
                    }
                    promoteBytes.add(char)
                    index += 1
                }
                moveDtos.add(MoveDto()
                        .from(PositionDto().x(fromX.toInt()).y(fromY.toInt()))
                        .to(PositionDto().x(toX.toInt()).y(toY.toInt()))
                        .takenPiece(PositionDto().x(takenX.toInt()).y(takenY.toInt()))
                        .promoteTo(PieceName.valueOf(promoteBytes.toByteArray().decodeToString()))
                )
            }
            moveCollectionDtos.add(MoveCollectionDto().moves(moveDtos))
            if (index >= blob.size) {
                break
            }
        }
        return moveCollectionDtos
    }

    private fun bytesToColourStatusDto(blob: List<Byte>): ColourStatusDto {
        val piecesBytes = blob.drop(2)
        val pieces = mutableListOf<PieceDto>()
        var index = 0
        while (index < blob.size - 2) {
            val xpos = piecesBytes[index]
            index += 1
            val ypos = piecesBytes[index]
            index += 1
            val pieceName = mutableListOf<Byte>()
            while (true) {
                val char = piecesBytes[index]
                if (char == 0.toByte()) {
                    break
                }
                pieceName.add(char)
                index += 1
            }
            pieces.add(PieceDto()
                    .position(PositionDto().x(xpos.toInt()).y(ypos.toInt()))
                    .name(PieceName.fromValue(pieceName.toByteArray().decodeToString()))
            )
        }

        return ColourStatusDto()
                .canCastleKingSide(blob[0].toBoolean())
                .canCastleQueenSide(blob[1].toBoolean())
                .pieces(pieces)
    }


    private fun ColourStatusDto.toDatabaseBlob(): Blob {
        val blob = mutableListOf<Byte>()
        blob.add(this.canCastleKingSide.toByte())
        blob.add(this.canCastleQueenSide.toByte())
        this.pieces.forEach { pieceDto ->
            blob.add(pieceDto.position.x.toByte())
            blob.add(pieceDto.position.y.toByte())
            blob += pieceDto.name.toBytesWithTerminator()
        }
//        val baos = ByteArrayOutputStream()
//        val oout = ObjectOutputStream(baos)
//        oout.writeObject(blob)
//        oout.close()
        val b = SerialBlob(blob.toByteArray())
        return b
    }

    private fun Boolean.toByte(): Byte {
        return when (this) {
            false -> 0.toByte()
            true -> 1.toByte()
        }
    }

    private fun Byte.toBoolean(): Boolean {
        return when (this) {
            0.toByte() -> false
            1.toByte() -> true
            else -> throw SchachbeInvalidState("expected boolean: got anything else")
        }
    }

    private fun PieceName?.toBytesWithTerminator(): List<Byte> {
        return this?.value?.toByteArray(Charsets.UTF_8)?.map { it }?.plus(0.toByte())
                ?: listOf(0.toByte())
    }

    private fun MoveCollectionDto.toDatabaseBlob(): List<Byte> {
        val blob = mutableListOf<Byte>()
        if (this.moves.size > 255) {
            throw SchachbeInvalidState("to many moves in move collection")
        }
        blob.add(this.moves.size.toByte())
        this.moves.forEach {
            blob.add(it.from.x.toByte())
            blob.add(it.from.y.toByte())
            blob.add(it.to.x.toByte())
            blob.add(it.to.y.toByte())
            blob.add(it.takenPiece.x.toByte())
            blob.add(it.takenPiece.y.toByte())
            blob += it.promoteTo.toBytesWithTerminator()
        }
        return blob
    }
}



