package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*

class Board() {
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

//    private val pieceMatrix: Array<Array<Piece?>> = Array(8) {
//        Array(8) {
//            null
//        }
//    }
    //private val pieceMatrix = arrayOf<Array<Piece?>>()
    private val pieceMatrix = Array(size = 8) { Array<Piece?>(size = 8) { null } }

    val pieces = mutableListOf<Piece>()

    init {
        pieceTypes.forEach {cls ->
            Colour.values().forEach { colour ->
                val piece = cls.call(colour, 0)
                addPiece(piece)
                for (id in 1 until piece.startingAmount) {
                    addPiece(cls.call(colour, id))
                }
            }
        }
    }

    private fun addPiece(piece: Piece) {
        pieces.add(piece)
        pieceMatrix[piece.position.x][piece.position.y] = piece
    }

    fun getLegalMoves(): List<Move> {
        val moves = mutableListOf<Move>()
        pieces.forEach {piece ->
            moves += piece.getLegalMoves()
        }
        return moves
    }

}