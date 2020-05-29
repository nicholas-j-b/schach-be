package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.service.EntityManagementService

class Queen(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "queen"
    override val startingAmount = 1
    override var position: Position = if (colour == Colour.black) Position(3, 0) else Position(3, 7)
    override fun move(to: Position) {
        TODO("Not yet implemented")
    }

    override fun getLegalMoves(): List<Move> {
        return emptyList()
    }
}
