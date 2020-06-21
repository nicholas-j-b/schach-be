package net.dummyvariables.games.schach.model.board

import net.dummyvariables.games.schach.model.util.BoardBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BoardTest {
    @Test
    fun `create sixteen pawns`() {

        val board = BoardBuilder.getBasicBoard()
        val pawns = board.entityManagementService.pieces.filter {
            it.pieceName == "pawn"
        }

        assertThat(pawns.size).isEqualTo(16)
    }
}