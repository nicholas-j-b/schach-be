package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Direction
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

class Pawn(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "pawn"
    override val startingAmount = 8
    override var position: Position = if (colour == Colour.black) Position(id, 1) else Position(id, 6)

    var hasMoved = false
        set(moved) {
            field = true
        }

    override fun move(to: Position) {
        position = to
        hasMoved = true
    }

    //TODO("diagonal take availabilities")
    override fun getLegalMoves(): MoveCollectionDto {
        val limit = if (hasMoved) 1 else 2
        val legalMoveDestinations = getLegalPositionsRay(position, getForward(), limit, false)
        return MoveCollectionDto(
                position, legalMoveDestinations.map { it }
        )
    }

    fun getForward(): Direction {
        return if (colour == Colour.black) Direction(0, 1) else Direction(0, -1)
    }
}
