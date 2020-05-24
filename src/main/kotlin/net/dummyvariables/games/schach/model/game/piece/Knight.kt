package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position

class Knight(
       override val colour: Colour,
       override val id: Int
) : Piece() {
    override val pieceName = "knight"
    override val startingAmount = 2
    override val position: Position = if (colour == Colour.black) Position(1 + id * 5, 0) else Position(1 + id * 5, 7)
    override fun getLegalMoves(): List<Move> {
        return emptyList()
    }
}
