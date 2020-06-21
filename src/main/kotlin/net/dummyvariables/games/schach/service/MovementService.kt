package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Game
import net.dummyvariables.games.schach.model.game.piece.Piece
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import org.springframework.stereotype.Service

@Service
class MovementService {
    fun move(moveDto: MoveDto, game: Game) {
        movePiece(moveDto, game.board)
        game.nextTurn()
    }

    fun movePiece(moveDto: MoveDto, board: Board) {
        takePieceIfNeed(board, moveDto)
        updatePiece(board, moveDto)
        board.entityManagementService.movePiece(moveDto)
    }

    private fun updatePiece(board: Board, moveDto: MoveDto) {
        val pieceToMove = getPieceToMove(board, moveDto)
        pieceToMove.move(moveDto.to, board)
    }

    private fun getPieceToMove(board: Board, moveDto: MoveDto): Piece {
        //TODO("this sometimes fails although the piece is moved correctly?")
        //TODO("sometimes a random other piece is taken")
        return board.entityManagementService.pieces.first {
            it.position == moveDto.from
        }
    }

    private fun takePieceIfNeed(board: Board, moveDto: MoveDto) {
        //TODO("add to 'taken pieces' or similar")
        board.entityManagementService.pieces.removeIf {
            it.position == moveDto.to
        }
    }

}