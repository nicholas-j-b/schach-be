package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import net.dummyvariables.games.schach.service.EntityManagementService
import net.dummyvariables.games.schach.service.MovementService

class King(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService,
        private val movementService: MovementService = MovementService()
) : Piece() {
    override val pieceName = "king"
    override val startingAmount = 1
    override var position: Position = if (colour == Colour.black) Position(4, 0) else Position(4, 7)
    val castleKingMoveDestinations: Map<BoardSide, Position>
    private val castleRookMoveDestinations: Map<BoardSide, Position>
    private val rookPositions: Map<BoardSide, Position>
    private val casteKingMoveDirection: Map<BoardSide, Direction>

    init {
        val rank = if (colour == Colour.black) 0 else 7
        castleKingMoveDestinations = mapOf(BoardSide.QUEEN to Position(2, rank), BoardSide.KING to Position(6, rank))
        castleRookMoveDestinations = mapOf(BoardSide.QUEEN to Position(3, rank), BoardSide.KING to Position(5, rank))
        rookPositions = mapOf(BoardSide.QUEEN to Position(0, rank), BoardSide.KING to Position(7, rank))
        casteKingMoveDirection = mapOf(BoardSide.QUEEN to Direction(-1, 0), BoardSide.KING to Direction(1, 0))
    }

    private val startingPosition = position
    var hasMoved = false

    override fun move(to: Position, board: Board) {
        hasMoved = true
        if (isMoveCastle(to)) {
            moveRookForCastle(to, board)
        }
        position = to
    }

    private fun moveRookForCastle(to: Position, board: Board) {
        val boardSide = castleKingMoveDestinations.filterValues {
            it == to
        }.keys.first()
        val rookPosition = rookPositions[boardSide]
        val rookDestination = castleRookMoveDestinations[boardSide]
        val moveDto = MoveDto(rookPosition!!, rookDestination!!)
        movementService.movePiece(moveDto, board)
    }

    private fun isMoveCastle(to: Position): Boolean {
        return position == startingPosition && to in castleKingMoveDestinations.values
    }

    override fun getLegalMoves(): MoveCollectionDto {
        val regularMoves = (RookDirections.directions + BishopDirections.directions).flatMap {
            getLegalPositionsRay(position, it, 1)
        }
        val castlingMoves = getCastleMoves()
        return MoveCollectionDto(position, (regularMoves + castlingMoves).map {
            MoveDestinationDto(it)
        })
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

}
