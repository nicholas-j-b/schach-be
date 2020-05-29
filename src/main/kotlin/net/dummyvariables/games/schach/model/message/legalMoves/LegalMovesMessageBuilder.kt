package net.dummyvariables.games.schach.model.message.legalMoves

import net.dummyvariables.games.schach.model.game.Move

class LegalMovesMessageBuilder {
    fun fromLegalMoves(legalMoves: List<Move>): LegalMovesMessage {
        val legalMoves = legalMoves.toDto()
        return LegalMovesMessage(legalMoves)
    }

    fun List<Move>.toDto(): List<MoveDto> {
        return this.map {
            MoveDto(it.from, it.to)
        }
    }

}
