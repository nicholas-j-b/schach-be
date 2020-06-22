package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.game.piece.BoardSide
import net.dummyvariables.games.schach.model.game.piece.King
import net.dummyvariables.games.schach.model.game.piece.Rook
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import net.dummyvariables.games.schach.model.util.BoardBuilder
import net.dummyvariables.games.schach.service.EntityManagementService
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MovementService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream

@SpringBootTest
class KingTest(
        @Autowired private val movementService: MovementService
) {
    companion object {
        val DEFAULT_COLOUR = Colour.black
        val DEFAULT_ID = 0

        @JvmStatic
        fun `legal moves in middle of board`(): Stream<Arguments> {
            val game = Game(EntityManagementService())
            val arguments = arrayOfNulls<Arguments?>(2)
            var arrayCounter = 0
            val startPos = Position(4, 4)
            listOf(Colour.white, Colour.black).forEach { colour: Colour ->
                val moves = mutableListOf<MoveDestinationDto>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(MoveDestinationDto(pos))
                        }
                    }
                }
                val king = King(colour, DEFAULT_ID, game.entityManagementService)
                king.move(startPos, game.board)
                arguments[arrayCounter] = Arguments.of(king, moves)
                arrayCounter++
            }
            return Stream.of(*arguments)
        }

        @JvmStatic
        fun `legal moves on edge of board`(): Stream<Arguments> {
            val game = Game(EntityManagementService())
            val arguments = arrayOfNulls<Arguments?>(2)
            var arrayCounter = 0
            val startPos = Position(0, 4)
            listOf(Colour.white, Colour.black).forEach { colour: Colour ->
                val moves = mutableListOf<MoveDestinationDto>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(MoveDestinationDto(pos))
                        }
                    }
                }
                val king = King(colour, DEFAULT_ID, game.entityManagementService)
                king.move(startPos, game.board)
                arguments[arrayCounter] = Arguments.of(king, moves)
                arrayCounter++
            }
            return Stream.of(*arguments)
        }

        @JvmStatic
        fun `legal moves on corner of board`(): Stream<Arguments> {
            val board = BoardBuilder.getEmptyBoard()
            val arguments = arrayOfNulls<Arguments?>(2)
            var arrayCounter = 0
            val startPos = Position(0, 0)
            listOf(Colour.white, Colour.black).forEach { colour: Colour ->
                val moves = mutableListOf<MoveDestinationDto>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(MoveDestinationDto(pos))
                        }
                    }
                }
                val king = King(colour, DEFAULT_ID, EntityManagementService())
                king.move(startPos, board)
                arguments[arrayCounter] = Arguments.of(king, moves)
                arrayCounter++
            }
            return Stream.of(*arguments)
        }
    }

    @Test
    fun `instantiate king`() {
        val board = BoardBuilder.getBasicBoard()

        val king = King(DEFAULT_COLOUR, IdUtil.getNextId(), board.entityManagementService)

        assertThat(king).isNotNull
    }

    @Test
    fun `instantiate king on empty board`() {
        val board = BoardBuilder.getEmptyBoard()

        val king = King(DEFAULT_COLOUR, IdUtil.getNextId(), board.entityManagementService)

        assertThat(king).isNotNull
    }

    @ParameterizedTest
    @MethodSource("legal moves in middle of board")
    fun `legal moves from middle of board are correct`(king: King, legalMoveDestinations: List<MoveDestinationDto>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(8)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @ParameterizedTest
    @MethodSource("legal moves on edge of board")
    fun `legal moves from board edge are correct`(king: King, legalMoveDestinations: List<MoveDestinationDto>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(5)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @ParameterizedTest
    @MethodSource("legal moves on corner of board")
    fun `legal moves from board corner are correct`(king: King, legalMoveDestinations: List<MoveDestinationDto>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(3)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @Test
    fun `king can castle kingside`() {
        val entityManagementService = EntityManagementService()
        val king = King(Colour.black, 0, entityManagementService)
        val rook = Rook(Colour.black, 1, entityManagementService)
        assertThat(rook.boardSide).isEqualTo(BoardSide.KING)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)

        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.indexOf(MoveDestinationDto(Position(6, 0)))).isNotEqualTo(-1)
    }

    @Test
    fun `king can castle queenside`() {
        val entityManagementService = EntityManagementService()
        val king = King(Colour.black, 0, entityManagementService)
        val rook = Rook(Colour.black, 0, entityManagementService)
        assertThat(rook.boardSide).isEqualTo(BoardSide.QUEEN)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)

        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.indexOf(MoveDestinationDto(Position(2, 0)))).isNotEqualTo(-1)
    }

    @Test
    fun `black king castles kingSide`() {
        val entityManagementService = EntityManagementService()
        val board = Board(entityManagementService)
        val king = King(Colour.black, 0, entityManagementService)
        val rook = Rook(Colour.black, 1, entityManagementService)
        val boardSide = BoardSide.KING
        assertThat(rook.boardSide).isEqualTo(boardSide)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)
        val kingDestination = king.castleKingMoveDestinations[boardSide]
        val moveDto = MoveDto(king.position, kingDestination!!)

        movementService.movePiece(moveDto, board)

        assertThat(king.position).isEqualTo(Position(6, 0))
        assertThat(rook.position).isEqualTo(Position(5, 0))
    }

    @Test
    fun `black king castles queenSide`() {
        val entityManagementService = EntityManagementService()
        val board = Board(entityManagementService)
        val king = King(Colour.black, 0, entityManagementService)
        val rook = Rook(Colour.black, 0, entityManagementService)
        val boardSide = BoardSide.QUEEN
        assertThat(rook.boardSide).isEqualTo(boardSide)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)
        val kingDestination = king.castleKingMoveDestinations[boardSide]
        val moveDto = MoveDto(king.position, kingDestination!!)

        movementService.movePiece(moveDto, board)

        assertThat(king.position).isEqualTo(Position(2, 0))
        assertThat(rook.position).isEqualTo(Position(3, 0))
    }

    @Test
    fun `white king castles kingSide`() {
        val entityManagementService = EntityManagementService()
        val board = Board(entityManagementService)
        val king = King(Colour.white, 0, entityManagementService)
        val rook = Rook(Colour.white, 1, entityManagementService)
        val boardSide = BoardSide.KING
        assertThat(rook.boardSide).isEqualTo(boardSide)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)
        val kingDestination = king.castleKingMoveDestinations[boardSide]
        val moveDto = MoveDto(king.position, kingDestination!!)

        movementService.movePiece(moveDto, board)

        assertThat(king.position).isEqualTo(Position(6, 7))
        assertThat(rook.position).isEqualTo(Position(5, 7))
    }

    @Test
    fun `white king castles queenSide`() {
        val entityManagementService = EntityManagementService()
        val board = Board(entityManagementService)
        val king = King(Colour.white, 0, entityManagementService)
        val rook = Rook(Colour.white, 0, entityManagementService)
        val boardSide = BoardSide.QUEEN
        assertThat(rook.boardSide).isEqualTo(boardSide)
        entityManagementService.addPieceToBoard(king)
        entityManagementService.addPieceToBoard(rook)
        val kingDestination = king.castleKingMoveDestinations[boardSide]
        val moveDto = MoveDto(king.position, kingDestination!!)

        movementService.movePiece(moveDto, board)

        assertThat(king.position).isEqualTo(Position(2, 7))
        assertThat(rook.position).isEqualTo(Position(3, 7))
    }
}