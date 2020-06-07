package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Direction
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService
import kotlin.math.abs

class KnightMoves {
    companion object {
        val directions: List<Direction> = listOf(-1, 1).flatMap { k ->
            listOf(1, 2).flatMap { i ->
                listOf(-i, i).map { j ->
                    val a = j * k
                    val b = k * (3 - i) * (i / abs(i))
                    Direction(a, b)
                }
            }
        }
    }
}

class Knight(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "knight"
    override val startingAmount = 2
    override var position: Position = if (colour == Colour.black) Position(1 + id * 5, 0) else Position(1 + id * 5, 7)
    override fun move(to: Position) {
        position = to
    }

    override fun getLegalMoves(): MoveCollectionDto {
        return MoveCollectionDto(position, KnightMoves.directions.mapNotNull { it.getNextPosition(position) })
    }
}
