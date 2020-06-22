package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
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
    var hasMoved = false

    val boardSide: BoardSide

    init {
        boardSide = if (id == 0) BoardSide.QUEEN else BoardSide.KING
    }

    override fun move(to: Position, board: Board) {
        hasMoved = true
        position = to
    }

    override fun getLegalMoves(): MoveCollectionDto {
        return MoveCollectionDto(position, RookDirections.directions.flatMap {
            getLegalPositionsRay(position, it)
        }.map {
            MoveDestinationDto(it)
        })
    }
}
