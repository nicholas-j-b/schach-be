package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
import net.dummyvariables.games.schach.service.EntityManagementService

class BishopDirections {
    companion object {
        val directions: List<Direction> = listOf(-1, 1).flatMap { i ->
            listOf(-1, 1).map { j ->
                Direction(i, j)
            }
        }
    }
}

class Bishop(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "bishop"
    override val startingAmount = 2
    override var position: Position = if (colour == Colour.black) Position(2 + id * 3, 0) else Position(2 + id * 3, 7)
    override fun move(to: Position, board: Board) {
        position = to
    }

    override fun getLegalMoves(): MoveCollectionDto {
        return MoveCollectionDto(position, BishopDirections.directions.flatMap {
            getLegalPositionsRay(position, it)
        }.map {
            MoveDestinationDto(it)
        })
    }
}
