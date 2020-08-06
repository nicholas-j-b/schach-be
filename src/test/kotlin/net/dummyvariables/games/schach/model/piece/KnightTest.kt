package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.piece.Knight
import net.dummyvariables.games.schach.model.game.piece.Pawn
import net.dummyvariables.games.schach.model.util.BoardBuilder
import net.dummyvariables.games.schach.service.EntityManagementService
import net.dummyvariables.games.schach.service.MovementService
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KnightTest(
        @Autowired private val movementService: MovementService
) {
    companion object {
        val DEFAULT_COLOUR = Colour.black
        val DEFAULT_ID = 0

//        @JvmStatic
//        fun `legal starting moves`(): Stream<Arguments> {
//            val arguments = arrayOfNulls<Arguments?>(16)
//            listOf(Colour.white, Colour.black).forEachIndexed { i: Int, colour: Colour ->
//                for (j in 0..7) {
//                    val legalY1 = if (colour == Colour.black) 2 else 5
//                    val legalY2 = if (colour == Colour.black) 3 else 4
//                    arguments[i * 8 + j] = Arguments.of(
//                            Pawn(colour, j, EntityManagementService()),
//                            listOf(
//                                    MoveDestinationDto(Position(j, legalY1)),
//                                    MoveDestinationDto(Position(j, legalY2))
//                            )
//                    )
//                }
//            }
//            return Stream.of(*arguments)
//        }
    }

    @Test
    fun `knight moves on empty board`() {
        val board = BoardBuilder.getEmptyBoard()
        val startingPosition = Position(4, 4);
        val knight = Knight(DEFAULT_COLOUR, 0, EntityManagementService())
        knight.position = startingPosition
        board.addPiece(knight)

        val legalMoves = knight.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(8)
    }

    @Test
    fun `knight moves include taking`() {
        val board = BoardBuilder.getEmptyBoard()
        val entityManagementService = EntityManagementService()
        val startingPosition = Position(4, 4);
        val knight = Knight(DEFAULT_COLOUR, 0, entityManagementService)
        knight.position = startingPosition
        board.addPiece(knight)
        val enemyPawn = Pawn(Colour.getOtherColour(DEFAULT_COLOUR), 0, entityManagementService)
        enemyPawn.position = Position(3, 2)
        board.addPiece(enemyPawn)

        val legalMoves = knight.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(8)
    }

    @Test
    fun `knight moves exclude own pieces`() {
        val board = BoardBuilder.getEmptyBoard()
        val entityManagementService = EntityManagementService()
        val startingPosition = Position(4, 4);
        val knight = Knight(DEFAULT_COLOUR, 0, entityManagementService)
        knight.position = startingPosition
        board.addPiece(knight)
        val friendlyPawn = Pawn(DEFAULT_COLOUR, 0, entityManagementService)
        friendlyPawn.position = Position(3, 2)
        board.addPiece(friendlyPawn)

        val legalMoves = knight.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(7)
    }
}