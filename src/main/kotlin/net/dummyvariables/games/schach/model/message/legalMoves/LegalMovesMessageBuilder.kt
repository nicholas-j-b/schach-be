package net.dummyvariables.games.schach.model.message.legalMoves

class LegalMovesMessageBuilder {
    fun fromLegalMoves(legalMoves: List<MoveCollectionDto>): LegalMovesMessage {
        return LegalMovesMessage(legalMoves)
    }

//    fun List<Move>.toDto(): MoveCollectionDto {
//        return this.map {
//            MoveDto(it.from, it.to)
//        }
//    }

}
