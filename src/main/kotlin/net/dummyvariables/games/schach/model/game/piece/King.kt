package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.service.EntityManagementService

class King(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "king"
    override val startingAmount = 1
    override var position: Position = if (colour == Colour.black) Position(4, 0) else Position(4, 7)
    private val castleKingMoveDestinations: Map<BoardSide, Position>
    private val castleRookMoveDestinations: Map<BoardSide, Position>
    private val casteKingMoveDirection: Map<BoardSide, Direction>

    init {
        val rank = if (colour == Colour.black) 0 else 7
        castleKingMoveDestinations = mapOf(BoardSide.QUEEN to Position(rank, 2), BoardSide.KING to Position(rank, 6))
        castleRookMoveDestinations = mapOf(BoardSide.QUEEN to Position(rank, 3), BoardSide.KING to Position(rank, 5))
        casteKingMoveDirection = mapOf(BoardSide.QUEEN to Direction(-1, 0), BoardSide.KING to Direction(1, 0))
    }

    private val startingPosition = position
    var hasMoved = false

    override fun move(to: Position, board: Board) {
        hasMoved = true
        if (isMoveCastle(to)) {
            entityManagementService
        }
        position = to
    }

    private fun isMoveCastle(to: Position): Boolean {
        return position == startingPosition && to in castleKingMoveDestinations.values
    }

    override fun getLegalMoves(): MoveCollectionDto {
        val regularMoves = (RookDirections.directions + BishopDirections.directions).flatMap {
            getLegalPositionsRay(position, it, 1)
        }
        val castlingMoves = getCastleMoves()
        return MoveCollectionDto(position, regularMoves + castlingMoves)
    }


    private fun getCastleMoves(): List<Position>{
        val options = mutableListOf<Position>()
        enumValues<BoardSide>().forEach { boardSide ->
            if (canCastle(boardSide)) {
                val direction = casteKingMoveDirection[boardSide] ?: error("king initialised improperly")
                direction.getNextPosition(position, 2)?.let { options.add(it) }
            }
        }
        return options
    }

    private fun canCastle(boardSide: BoardSide): Boolean {
        return when {
            hasMoved -> false
            hasRookMoved(boardSide) -> false
            !squaresBetweenFree(boardSide) -> false
            else -> true
        }
    }

    private fun squaresBetweenFree(boardSide: BoardSide): Boolean {
        val direction = casteKingMoveDirection[boardSide] ?: error("invalid boardside $boardSide")
        return getLegalPositionsRay(position, direction, 2, false).size == 2
    }

    private fun hasRookMoved(boardSide: BoardSide): Boolean {
        val rook = entityManagementService.getRookBySide(colour, boardSide) as Rook?
        return rook?.hasMoved ?: true
    }

//    private fun getNextOccupancy(positionToTest: Position): Pair<SquareOccupancyType?, Position?> {
//        val square = kingSideCastleDirection.getNextPosition(positionToTest)
//        val occupancy = square?.let {
//            entityManagementService.checkSquareOccupancyType(it)
//        }
//        return Pair(occupancy, square)
//    }
}
