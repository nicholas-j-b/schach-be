package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.Colour
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

        assertThat(legalMoves.size).isEqualTo(2)
        legalMoves.forEach {
            assertThat(it.from).isEqualTo(pawn.position)
            assertThat(legalMoveDestinations.indexOf(it.to)).isNotEqualTo(-1)
        }
    }

    //TODO("take pieces")
    //TODO("piece promotion")
    //TODO("disallow self discovering check")
}