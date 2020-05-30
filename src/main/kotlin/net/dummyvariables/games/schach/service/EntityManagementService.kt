package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.SquareOccupancyType
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

    fun checkSquareOccupancyType(position: Position): SquareOccupancyType {
        val isOccupied = pieceMatrix.elementAtOrNull(position.x)?.elementAtOrNull(position.y) != null
        val isSquare = position.x in 0..7 && position.y in 0..7
        val colour = pieceMatrix.elementAtOrNull(position.x)?.elementAtOrNull(position.y)?.colour
        return SquareOccupancyType(isOccupied, isSquare, colour)
    }
}