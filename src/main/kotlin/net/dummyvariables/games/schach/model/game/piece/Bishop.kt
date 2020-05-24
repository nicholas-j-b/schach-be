package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position

class Bishop(
       override val colour: Colour,
       override val id: Int
) : Piece() {
    override val pieceName = "bishop"
    override val startingAmount = 2
    override val position: Position = if (colour == Colour.black) Position(2 + id * 3, 0) else Position(2 + id * 3, 7)
    override fun getLegalMoves(): List<Move> {
        return emptyList()
    }
}
