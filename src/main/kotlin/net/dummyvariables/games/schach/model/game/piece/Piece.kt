package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Direction
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.service.EntityManagementService

abstract class Piece {
    abstract val colour: Colour
    abstract val id: Int
    abstract val entityManagementService: EntityManagementService
    abstract val pieceName: String
    abstract var position: Position
    abstract val startingAmount: Int
    abstract fun move(to: Position)
    abstract fun getLegalMoves(): List<Move>

    fun getLegalPositionsRay(startingPosition: Position, direction: Direction, limit: Int?): List<Position> {
        val legalMoveDestinations = mutableListOf<Position>()
        var positionToTest = startingPosition
        loop@ for (i in 0 until (limit ?: 8)) {
            positionToTest = direction.getNextPosition(positionToTest)
            val isOccupied = entityManagementService.checkSquareOccupied(positionToTest)
            when  {
                isOccupied == null -> legalMoveDestinations.add(positionToTest)
                isOccupied != colour -> { legalMoveDestinations.add(positionToTest); break@loop }
                else -> break@loop
            }
        }
        return legalMoveDestinations
    }
}
