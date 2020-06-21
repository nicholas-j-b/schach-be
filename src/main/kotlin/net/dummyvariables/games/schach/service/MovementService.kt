package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.StandardBoard
import net.dummyvariables.games.schach.model.game.Game
import net.dummyvariables.games.schach.model.game.piece.Piece
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import org.springframework.stereotype.Service

@Service
class MovementService {
    fun move(moveDto: MoveDto, game: Game) {
        val board = game.board
        takePieceIfNeed(board, moveDto)
        updatePiece(board, moveDto)
        board.entityManagementService.movePiece(moveDto)
        game.nextTurn()
    }

    private fun updatePiece(board: StandardBoard, moveDto: MoveDto) {
        val pieceToMove = getPieceToMove(board, moveDto)
        pieceToMove.move(moveDto.to, board)
    }

    private fun getPieceToMove(board: StandardBoard, moveDto: MoveDto): Piece {
        //TODO("this sometimes fails although the piece is moved correctly?")
        //TODO("sometimes a random other piece is taken")
        return board.entityManagementService.pieces.first {
            it.position == moveDto.from
        }
    }

    private fun takePieceIfNeed(board: StandardBoard, moveDto: MoveDto) {
        //TODO("add to 'taken pieces' or similar")
        board.entityManagementService.pieces.removeIf {
            it.position == moveDto.to
        }
    }

}