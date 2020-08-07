package net.dummyvariables.games.schach.model.piece

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.game.piece.Knight
import net.dummyvariables.games.schach.model.game.piece.Pawn
import net.dummyvariables.games.schach.model.util.BoardBuilder
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KnightTest() {
    companion object {
        val DEFAULT_COLOUR = Colour.black
    }

    @Test
    fun `knight moves on empty board`() {
        val board = BoardBuilder.getEmptyBoard()
        val startingPosition = Position(4, 4);
        val knight = Knight(DEFAULT_COLOUR, 0, board.entityManagementService)
        knight.position = startingPosition
        board.addPiece(knight)

        val legalMoves = knight.getLegalMoves()

        assertThat(legalMoves.to.size).isEqualTo(8)
    }

    @Test
    fun `knight moves include taking`() {
        val board = BoardBuilder.getEmptyBoard()
        val entityManagementService = board.entityManagementService
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
        val entityManagementService = board.entityManagementService
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