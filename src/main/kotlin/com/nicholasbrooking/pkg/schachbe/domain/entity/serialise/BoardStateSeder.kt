package com.nicholasbrooking.pkg.schachbe.domain.entity.serialise

import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidState
import org.springframework.stereotype.Service
import java.sql.Blob
import javax.sql.rowset.serial.SerialBlob


@ExperimentalStdlibApi
@Service
class BoardStateSeder {
    private var index: Int = 0

    fun bytesToHistory(blob: Blob): List<MoveCollectionDto> {
        val bytes = blob.getBytes(1, blob.length().toInt())
        index = 0
        val moveCollectionDtos = mutableListOf<MoveCollectionDto>()
        while (true) {
            val numMoves = bytes[index]
            index += 1
            val moveDtos = mutableListOf<MoveDto>()
            for (i in 0 until numMoves) {
                index = deserialiseMove(bytes, moveDtos)
            }
            moveCollectionDtos.add(MoveCollectionDto().moves(moveDtos))
            if (index >= bytes.size) {
                break
            }
        }
        return moveCollectionDtos
    }

    private fun deserialiseMove(bytes: ByteArray, moveDtos: MutableList<MoveDto>): Int {
        val (fromX, fromY) = deserialisePosition(bytes)
        val (toX, toY) = deserialisePosition(bytes)
        val isTakenPresent = bytes[index] == 1.toByte()
        index += 1
        var takenX: Byte? = null
        var takenY: Byte? = null
        if (isTakenPresent) {
            val (takenXin, takenYin) = deserialisePosition(bytes)
            takenX = takenXin
            takenY = takenYin
        }
        val isPromotePresent = bytes[index] == 1.toByte()
        index += 1
        var promoteBytes: MutableList<Byte>? = null
        if (isPromotePresent) {
            promoteBytes = mutableListOf<Byte>()
            while (true) {
                val char = bytes[index]
                if (char == 0.toByte()) {
                    index += 1
                    break
                }
                promoteBytes.add(char)
                index += 1
            }
        }
        val moveDto = buildMoveDto(fromX, fromY, toX, toY, isTakenPresent, takenX, takenY, isPromotePresent, promoteBytes)
        moveDtos.add(moveDto)
        return index
    }

    private fun buildMoveDto(fromX: Byte, fromY: Byte, toX: Byte, toY: Byte, isTakenPresent: Boolean, takenX: Byte?, takenY: Byte?, isPromotePresent: Boolean, promoteBytes: MutableList<Byte>?): MoveDto {
        val moveDto = MoveDto()
                .from(PositionDto().x(fromX.toInt()).y(fromY.toInt()))
                .to(PositionDto().x(toX.toInt()).y(toY.toInt()))
        if (isTakenPresent) {
            moveDto.takenPiece(PositionDto().x(takenX?.toInt()).y(takenY?.toInt()))
        }
        if (isPromotePresent) {
            moveDto.promoteTo(PieceName.fromValue(promoteBytes?.toByteArray()?.decodeToString()))
        }
        return moveDto
    }

    private fun deserialisePosition(bytes: ByteArray): Triple<Byte, Byte, Int> {
        val fromX = bytes[index]
        index += 1
        val fromY = bytes[index]
        index += 1
        return Triple(fromX, fromY, index)
    }

    fun bytesToColourStatusDto(blob: Blob): ColourStatusDto {
        val bytes = blob.getBytes(1, blob.length().toInt())
        val piecesBytes = bytes.drop(2)
        val pieces = mutableListOf<PieceDto>()
        index = 0
        while (index < bytes.size - 2) {
            val xpos = piecesBytes[index]
            index += 1
            val ypos = piecesBytes[index]
            index += 1
            val pieceName = mutableListOf<Byte>()
            while (true) {
                val char = piecesBytes[index]
                if (char == 0.toByte()) {
                    index += 1
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
                .canCastleKingSide(bytes[0].toBoolean())
                .canCastleQueenSide(bytes[1].toBoolean())
                .pieces(pieces)
    }


    fun colourStatusDtoToDatabaseBlob(colourStatusDto: ColourStatusDto): Blob {
        val blob = mutableListOf<Byte>()
        blob.add(colourStatusDto.canCastleKingSide.toByte())
        blob.add(colourStatusDto.canCastleQueenSide.toByte())
        colourStatusDto.pieces.forEach { pieceDto ->
            blob.add(pieceDto.position.x.toByte())
            blob.add(pieceDto.position.y.toByte())
            blob += pieceDto.name.toBytesWithTerminator()
        }
        return SerialBlob(blob.toByteArray())
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

    fun moveCollectionDtoToListOfBytes(moveCollectionDto: MoveCollectionDto): List<Byte> {
        val blob = mutableListOf<Byte>()
        if (moveCollectionDto.moves.size > 255) {
            throw SchachbeInvalidState("to many moves in move collection")
        }
        blob.add(moveCollectionDto.moves.size.toByte())
        moveCollectionDto.moves.forEach {
            blob.add(it.from.x.toByte())
            blob.add(it.from.y.toByte())
            blob.add(it.to.x.toByte())
            blob.add(it.to.y.toByte())
            if (it.takenPiece != null) {
                blob.add(1.toByte())
                blob.add(it.takenPiece.x.toByte())
                blob.add(it.takenPiece.y.toByte())
            } else {
                blob.add(0.toByte())
            }
            if (it.promoteTo != null) {
                blob.add(1.toByte())
                blob += it.promoteTo.toBytesWithTerminator()
            } else {
                blob.add(0.toByte())
            }
        }
        return blob
    }

    fun historyToDatabaseBlob(moveCollectionDtos: List<MoveCollectionDto>): Blob {
        return SerialBlob(
                moveCollectionDtos.flatMap {
                    moveCollectionDtoToListOfBytes(it)
                }.toByteArray()
        )
    }
}