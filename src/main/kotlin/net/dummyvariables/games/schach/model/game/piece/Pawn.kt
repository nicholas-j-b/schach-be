package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import net.dummyvariables.games.schach.service.EntityManagementService

class Pawn(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "pawn"
    override val startingAmount = 8
    override var position: Position = if (colour == Colour.black) Position(id, 1) else Position(id, 6)
    private val promotionRank = if (colour == Colour.black) 7 else 0

    var hasMoved = false
        set(moved) {
            field = true
        }

    override fun move(to: Position, board: Board) {
        hasMoved = true
        position = to
        if (isPawnPromotion(to)) {
            entityManagementService.takePieceIfExists(to)
            val newPiece = Queen(colour, board.getNextPromotionId(), entityManagementService)
            newPiece.position = to
            entityManagementService.addPieceToBoard(newPiece)
        }
    }

    override fun getLegalMoves(): MoveCollectionDto {
        val limit = if (hasMoved) 1 else 2
        val legalForwardMoveDestinations = getLegalPositionsRay(position, getForward(), limit, false)
        val legalDiagonalMoveDestinations = getDiagonals()
        return MoveCollectionDto(
                position, (legalForwardMoveDestinations + legalDiagonalMoveDestinations).map { MoveDestinationDto(it, isPawnPromotion(it)) }
        )
    }

    fun getForward(): Direction {
        return if (colour == Colour.black) Direction(0, 1) else Direction(0, -1)
    }

    fun getDiagonals(): List<Position> {
        val forwards = getForward().y
        return listOf(Direction(1, forwards), Direction(-1, forwards)).mapNotNull { direction ->
            direction.getNextPosition(position)?.let {
                if (moveTakesPiece(MoveDto(position, it))) it else null
            }
        }
    }

    private fun isPawnPromotion(to: Position): Boolean {
        return to.y == promotionRank
    }

}
