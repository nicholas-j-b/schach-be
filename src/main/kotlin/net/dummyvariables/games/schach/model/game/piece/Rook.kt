package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Direction
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

class RookDirections {
    companion object {
        val directions: List<Direction> = listOf(-1, 1).flatMap { i ->
            listOf(0, 1).map { j ->
                val a = i * j
                val b = (i * -1) * (1 - j)
                Direction(a, b)
            }
        }
    }
}

class Rook(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "rook"
    override val startingAmount = 2
    override var position: Position = if (colour == Colour.black) Position(id * 7, 0) else Position(id * 7, 7)
    override fun move(to: Position) {
        position = to
    }

    override fun getLegalMoves(): MoveCollectionDto {
        return MoveCollectionDto(position, RookDirections.directions.flatMap {
            getLegalPositionsRay(position, it)
        }.map {
            it
        })
    }
}
