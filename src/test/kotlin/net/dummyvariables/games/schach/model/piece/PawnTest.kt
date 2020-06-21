package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Move
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.piece.Pawn
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
class PawnTest {
    companion object {
        val DEFAULT_COLOUR = Colour.black
        val DEFAULT_ID = 0

        @JvmStatic
        fun `legal starting moves`(): Stream<Arguments> {
            val arguments = arrayOfNulls<Arguments?>(16)
            listOf(Colour.white, Colour.black).forEachIndexed { i: Int, colour: Colour ->
                for (j in 0..7) {
                    val legalY1 = if (colour == Colour.black) 2 else 5
                    val legalY2 = if (colour == Colour.black) 3 else 4
                    arguments[i * 8 + j] = Arguments.of(
                            Pawn(colour, j, EntityManagementService()),
                            listOf(Position(j, legalY1), Position(j, legalY2))
                    )
                }
            }
            return Stream.of(*arguments)
        }
    }

    @Test
    fun `instantiate pawn`() {
        val board = BoardBuilder.getBasicBoard()

        val pawn = Pawn(DEFAULT_COLOUR, IdUtil.getNextId(), board.entityManagementService)

        assertThat(pawn).isNotNull
    }

    @ParameterizedTest
    @MethodSource("legal starting moves")
    fun `legal starting moves are correct`(pawn: Pawn, legalMoveDestinations: List<Position>) {
        val legalMoves = pawn.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(2)
        legalMoves.to.forEach {
            assertThat(legalMoves.from).isEqualTo(pawn.position)
            assertThat(legalMoveDestinations.indexOf(it)).isNotEqualTo(-1)
        }
    }

    @Test
    fun `pawn moves on empty board`() {
        val startingPosition = Position(5, 5)
        val (pawn, expectedEndPosition) = getPawnAndEndPositionOnEmptyBoard(DEFAULT_COLOUR, startingPosition)

        val legalMoves = pawn.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(1)
        val expectedMove = Move(startingPosition, expectedEndPosition!!)
        val actualMove = Move(legalMoves.from, legalMoves.to.first())
        assertThat(actualMove).isEqualTo(expectedMove)
    }

//    @Test
//    fun `pawn promotes to queen in last row`() {
//        val startingPosition = Position(4, 6)
//        val (pawn, expectedEndPosition) = getPawnAndEndPositionOnEmptyBoard(Colour.black, startingPosition)
//
//        val
//    }

    //TODO("take pieces")
    //TODO("piece promotion")
    //TODO("disallow self discovering check")


    private fun getPawnAndEndPositionOnEmptyBoard(colour: Colour, startingPosition: Position): Pair<Pawn, Position?> {
        val board = BoardBuilder.getEmptyBoard()
        val pawn = Pawn(colour, 0, EntityManagementService())
        val direction = pawn.getForward()
        val expectedEndPosition = direction.getNextPosition(startingPosition)
        pawn.position = startingPosition
        pawn.hasMoved = true
        board.addPiece(pawn)
        return Pair(pawn, expectedEndPosition)
    }
}