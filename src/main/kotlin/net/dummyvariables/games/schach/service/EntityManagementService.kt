package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.piece.Piece

class EntityManagementService {
    private val pieceMatrix = Array(size = 8) { Array<Piece?>(size = 8) { null } }

    fun addPieceToBoard(piece: Piece) {
        pieceMatrix[piece.position.x][piece.position.y] = piece
    }

    fun checkSquareOccupied(position: Position): Colour? {
        return pieceMatrix[position.x][position.y]?.colour
    }
}