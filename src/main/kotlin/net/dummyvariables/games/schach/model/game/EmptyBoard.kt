package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*
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

    fun addPiece(piece: Piece) {
        pieces.add(piece)
        entityManagementService.addPieceToBoard(piece)
    }

    fun getLegalMoves(): List<Move> {
        val moves = mutableListOf<Move>()
        pieces.forEach {piece ->
            moves += piece.getLegalMoves()
        }
        return moves
    }

}