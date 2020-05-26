package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.service.EntityManagementService

class Rook(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "rook"
    override val startingAmount = 2
    override val position: Position = if (colour == Colour.black) Position(id * 7, 0) else Position(id * 7, 7)
    override fun getLegalMoves(): List<Move> {
        return emptyList()
    }
}
