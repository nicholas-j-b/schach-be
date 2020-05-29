package net.dummyvariables.games.schach.model.message.pieces

import net.dummyvariables.games.schach.model.game.piece.Piece

class PieceMessageBuilder {
    fun fromPieceList(pieces: List<Piece>): PieceMessage {
        val pieceDtos = pieces.toDto()
        return PieceMessage(pieceDtos)
    }

    fun List<Piece>.toDto(): List<PieceDto> {
        return this.map {
            PieceDto(it.colour, it.id, it.pieceName, it.position)
        }
    }
}


