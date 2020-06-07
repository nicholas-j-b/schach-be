package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.piece.Piece
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class MovementService {
    fun move(moveDto: MoveDto, board: Board) {
        takePieceIfNeed(board, moveDto)
        updatePiece(board, moveDto)
        board.entityManagementService.movePiece(moveDto)
    }

    private fun updatePiece(board: Board, moveDto: MoveDto) {
        val pieceToMove = getPieceToMove(board, moveDto)
        pieceToMove.move(moveDto.to)
    }

    private fun getPieceToMove(board: Board, moveDto: MoveDto): Piece {
        //TODO("this sometimes fails although the piece is moved correctly?")
        //TODO("sometimes a random other piece is taken")
        val a = Random.nextBoolean()
        return board.pieces.first {
            it.position == moveDto.from
        }
    }

    private fun takePieceIfNeed(board: Board, moveDto: MoveDto) {
        //TODO("add to 'taken pieces' or similar")
        board.pieces.removeIf {
            it.position == moveDto.to
        }
    }

}