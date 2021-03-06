package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

open class Board (
        val entityManagementService: EntityManagementService
) {
    companion object {
        val pieceTypes = listOf(
                King::class,
                Queen::class,
                Pawn::class,
                Rook::class,
                Bishop::class,
                Knight::class
        )
    }

    private var promotionId = 10
    var currentColour = Colour.white

    fun newTurn(): Colour {
        currentColour = if (currentColour == Colour.white) Colour.black else Colour.white
        return currentColour
    }

    fun addPiece(piece: Piece) {
        entityManagementService.addPieceToBoard(piece)
    }

    fun getNextPromotionId(): Int {
        return promotionId++
    }
}