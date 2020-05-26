package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*
import net.dummyvariables.games.schach.service.EntityManagementService

class Board(
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

    init {
        pieceTypes.forEach {cls ->
            Colour.values().forEach { colour ->
                val piece = cls.call(colour, 0, entityManagementService)
                addPiece(piece)
                for (id in 1 until piece.startingAmount) {
                    addPiece(cls.call(colour, id, entityManagementService))
                }
            }
        }
    }

    private fun addPiece(piece: Piece) {
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