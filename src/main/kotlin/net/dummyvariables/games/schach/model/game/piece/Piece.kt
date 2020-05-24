package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position

abstract class Piece {
        abstract val colour: Colour
        abstract val pieceName: String
        abstract val id: Int
        abstract val position: Position
        abstract val startingAmount: Int
        abstract fun getLegalMoves(): List<Move>
}
