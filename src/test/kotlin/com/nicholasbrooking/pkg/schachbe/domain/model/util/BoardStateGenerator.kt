package com.nicholasbrooking.pkg.schachbe.domain.model.util

import com.nicholasbrooking.pkg.schachbe.api.model.*

class BoardStateGenerator {
    companion object {
        fun getDefaultBoardStateDto(): BoardStateDto {
            return BoardStateDto()
                    .blackStatus(getDefaultColourStatusDto(1))
                    .whiteStatus(getDefaultColourStatusDto(2))
                    .turn(Colour.BLACK)
                    .history(getDefaultHistory())
        }

        fun getDefaultHistory(): List<MoveCollectionDto> {
            return listOf(
                    MoveCollectionDto()
                            .moves(listOf(
                                    MoveDto()
                                            .from(PositionDto().x(1).y(1))
                                            .to(PositionDto().x(2).y(2))
                                            .takenPiece(PositionDto().x(3).y(4))
                                            .promoteTo(PieceName.QUEEN)
                            )),
                    MoveCollectionDto()
                            .moves(listOf(
                                    MoveDto()
                                            .from(PositionDto().x(3).y(3))
                                            .to(PositionDto().x(4).y(4))
                                            .takenPiece(PositionDto().x(3).y(4))
                                            .promoteTo(PieceName.QUEEN)
                            ))
            )
        }

        fun getDefaultColourStatusDto(x: Int = 1): ColourStatusDto {
            return ColourStatusDto()
                    .canCastleKingSide(false)
                    .canCastleQueenSide(false)
                    .pieces(listOf(
                            PieceDto()
                                    .position(PositionDto().x(x).y(1))
                                    .name(PieceName.KING),
                            PieceDto()
                                    .position(PositionDto().x(x).y(2))
                                    .name(PieceName.QUEEN),
                            PieceDto()
                                    .position(PositionDto().x(x).y(3))
                                    .name(PieceName.BISHOP),

                            PieceDto()
                                    .position(PositionDto().x(x).y(4))
                                    .name(PieceName.KNIGHT),
                            PieceDto()
                                    .position(PositionDto().x(x).y(5))
                                    .name(PieceName.ROOK),
                            PieceDto()
                                    .position(PositionDto().x(x).y(6))
                                    .name(PieceName.PAWN)
                    ))
        }

        fun getBoardStateDtoWithMissingFields(): BoardStateDto {
            return BoardStateDto()
                    .whiteStatus(getDefaultColourStatusDto())
                    .blackStatus(getDefaultColourStatusDto())
                    .turn(Colour.BLACK)
                    .history(getHistoryWithMissingFields())
        }

        fun getHistoryWithMissingFields(): List<MoveCollectionDto> {
            return listOf(
                    MoveCollectionDto()
                            .moves(listOf(
                                    MoveDto()
                                            .from(PositionDto().x(1).y(1))
                                            .to(PositionDto().x(2).y(2))
                                            .promoteTo(PieceName.QUEEN)
                            )),
                    MoveCollectionDto()
                            .moves(listOf(
                                    MoveDto()
                                            .from(PositionDto().x(3).y(3))
                                            .to(PositionDto().x(4).y(4))
                                            .takenPiece(PositionDto().x(3).y(4)),
                                    MoveDto()
                                            .from(PositionDto().x(3).y(3))
                                            .to(PositionDto().x(4).y(4))
                            ))
            )
        }

    }
}
