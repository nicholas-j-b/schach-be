package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.*
import net.dummyvariables.games.schach.model.game.piece.King
import net.dummyvariables.games.schach.model.util.BoardBuilder
import net.dummyvariables.games.schach.service.EntityManagementService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Stream

@SpringBootTest
class KingTest {
    companion object {
        val DEFAULT_COLOUR = Colour.black
        val DEFAULT_ID = 0

        @JvmStatic
        fun `legal moves in middle of board`(): Stream<Arguments> {
            val game = Game(EntityManagementService())
            val arguments = arrayOfNulls<Arguments?>(2)
            var arrayCounter = 0
            val startPos = Position(4, 4)
            listOf(Colour.white, Colour.black).forEachIndexed { i: Int, colour: Colour ->
                val moves = mutableListOf<Position>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(pos)
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
            listOf(Colour.white, Colour.black).forEachIndexed { i: Int, colour: Colour ->
                val moves = mutableListOf<Position>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(pos)
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
            listOf(Colour.white, Colour.black).forEachIndexed { i: Int, colour: Colour ->
                val moves = mutableListOf<Position>()
                for (j in -1..1) {
                    for (k in -1..1) {
                        if (j != 0 || k != 0) {
                            val pos = Position(startPos.x + j, startPos.y + k)
                            moves.add(pos)
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
    fun `legal moves from middle of board are correct`(king: King, legalMoveDestinations: List<Position>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(8)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @ParameterizedTest
    @MethodSource("legal moves on edge of board")
    fun `legal moves from board edge are correct`(king: King, legalMoveDestinations: List<Position>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(5)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @ParameterizedTest
    @MethodSource("legal moves on corner of board")
    fun `legal moves from board corner are correct`(king: King, legalMoveDestinations: List<Position>) {
        val legalMoves = king.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(3)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(king.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

//    @Test
//    fun `pawn moves on empty board`() {
//        val startingPosition = Position(5, 5)
//        val (pawn, expectedEndPosition) = getPawnAndEndPositionOnEmptyBoard(DEFAULT_COLOUR, startingPosition)
//
//        val legalMoves = pawn.getLegalMoves()
//
//        assertThat(legalMoves.to.size).isEqualTo(1)
//        val expectedMove = Move(startingPosition, expectedEndPosition!!)
//        val actualMove = Move(legalMoves.from, legalMoves.to.first())
//        assertThat(actualMove).isEqualTo(expectedMove)
//    }
//
////    @Test
////    fun `pawn promotes to queen in last row`() {
////        val startingPosition = Position(4, 6)
////        val (pawn, expectedEndPosition) = getPawnAndEndPositionOnEmptyBoard(Colour.black, startingPosition)
////
////        val
////    }
//
//    //TODO("take pieces")
//    //TODO("piece promotion")
//    //TODO("disallow self discovering check")
//
//
//    private fun getPawnAndEndPositionOnEmptyBoard(colour: Colour, startingPosition: Position): Pair<Pawn, Position?> {
//        val board = BoardBuilder.getEmptyBoard()
//        val pawn = Pawn(colour, 0, EntityManagementService())
//        val direction = pawn.getForward()
//        val expectedEndPosition = direction.getNextPosition(startingPosition)
//        pawn.position = startingPosition
//        pawn.hasMoved = true
//        board.addPiece(pawn)
//        return Pair(pawn, expectedEndPosition)
//    }
}