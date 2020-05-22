package net.dummyvariables.games.schach.model.game

class Board() {
    val pieces = mutableListOf<Piece>()

    init {
        pieces.add(Piece("white", "queen", 0, Position(3, 3)))
    }

}