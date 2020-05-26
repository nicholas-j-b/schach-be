package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.service.EntityManagementService

class King(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "king"
    override val startingAmount = 1
    override val position: Position = if (colour == Colour.black) Position(4, 0) else Position(4, 7)
    override fun getLegalMoves(): List<Move> {
        return emptyList()
    }
}
