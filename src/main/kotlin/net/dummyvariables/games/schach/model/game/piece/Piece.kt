package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

abstract class Piece {
    abstract val colour: Colour
    abstract val id: Int
    abstract val entityManagementService: EntityManagementService
    abstract val pieceName: String
    abstract var position: Position
    abstract val startingAmount: Int
    abstract fun move(to: Position, board: Board)
    abstract fun getLegalMoves(): MoveCollectionDto

    fun getLegalPositionsRay(startingPosition: Position, direction: Direction, limit: Int? = null, take: Boolean = true): List<Position> {
        val legalMoveDestinations = mutableListOf<Position>()
        var positionToTest  = startingPosition
        loop@ for (i in 0 until (limit ?: 8)) {
            positionToTest = direction.getNextPosition(positionToTest) ?: break@loop
            val occupancy = entityManagementService.checkSquareOccupancyType(positionToTest)
            when {
                occupancy.isSquare && !occupancy.isOccupied -> legalMoveDestinations.add(positionToTest)
                occupancy.isSquare && occupancy.colour != colour && take -> { legalMoveDestinations.add(positionToTest); break@loop }
                else -> break@loop
            }
        }
        return legalMoveDestinations
    }

    // assumes valid move
    fun moveTakesPiece(move: Move): Boolean {
        val fromOccupancy = entityManagementService.checkSquareOccupancyType(move.from)
        val toOccupancy = entityManagementService.checkSquareOccupancyType(move.to)
        return  toOccupancy.isSquare &&
                toOccupancy.isOccupied &&
                toOccupancy.colour != fromOccupancy.colour
    }
}
