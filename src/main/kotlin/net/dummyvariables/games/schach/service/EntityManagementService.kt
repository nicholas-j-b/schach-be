package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.piece.Piece
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto

class EntityManagementService {
    private val pieceMatrix = Array(size = 8) { Array<Piece?>(size = 8) { null } }

    fun addPieceToBoard(piece: Piece) {
        pieceMatrix[piece.position.x][piece.position.y] = piece
    }

    fun movePiece(moveDto: MoveDto) {
        pieceMatrix[moveDto.to.x][moveDto.to.y] = pieceMatrix[moveDto.from.x][moveDto.from.y]
        pieceMatrix[moveDto.from.x][moveDto.from.y] = null
    }

    fun checkSquareOccupied(position: Position): Colour? {
        return pieceMatrix[position.x][position.y]?.colour
    }
}