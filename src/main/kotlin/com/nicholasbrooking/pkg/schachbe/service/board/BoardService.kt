package com.nicholasbrooking.pkg.schachbe.service.board

import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.client.BoardClient
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameType
import org.springframework.stereotype.Service

@Service
class BoardService(
        private val boardClient: BoardClient
) {
    fun getAllActiveBoardIds(): List<Long> {
        return boardClient.getAllIds()
    }

    fun createBoardStateDto(gameType: GameType): BoardStateDto {
        return BoardStateDto()
                .blackStatus(
                        ColourStatusDto()
                                .canCastleKingSide(true)
                                .canCastleQueenSide(true)
                                .pieces(
                                        listOf(
                                                PieceDto()
                                                        .name(PieceName.KING)
                                                        .position(
                                                                PositionDto()
                                                                        .x(3)
                                                                        .y(3)
                                                        )
                                        )
                                )
                )
                .whiteStatus(
                        ColourStatusDto()
                                .canCastleKingSide(true)
                                .canCastleQueenSide(true)
                                .pieces(emptyList())
                )
                .turn(Colour.BLACK)
                .history(emptyList())
    }

    fun createBoardFromState(boardStateDto: BoardStateDto): Long? {
        return boardClient.createBoardFromState(boardStateDto)
    }
}