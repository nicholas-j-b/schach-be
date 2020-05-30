package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

open class EmptyBoard (
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
        ).map { it.constructors.first() }
    }

    val pieces = mutableListOf<Piece>()
    var currentColour = Colour.white

    fun newTurn(): Colour {
        val nextColour = if (currentColour == Colour.white) Colour.black else Colour.white
        currentColour = nextColour
        return nextColour
    }

    fun addPiece(piece: Piece) {
        pieces.add(piece)
        entityManagementService.addPieceToBoard(piece)
    }

    fun getLegalMoves(): List<MoveCollectionDto> {
        val moves = mutableListOf<MoveCollectionDto>()
        pieces.forEach {piece ->
            if (piece.colour == currentColour) {
                moves += piece.getLegalMoves()
            }
        }
        return moves
    }

}