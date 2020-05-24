package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position

class Pawn(
       override val colour: Colour,
       override val id: Int
) : Piece() {
    override val pieceName = "pawn"
    override val startingAmount = 8
    override val position: Position = if (colour == Colour.black) Position(id, 1) else Position(id, 6)
    override fun getLegalMoves(): List<Move> {
        return listOf(Move(position, Position(4, 4)))
    }
}
