package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Colour
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
class GameServiceTest(
        @Autowired private val gameService: GameService
) {
    @Test
    fun `convert connectionId into gameId`() {
        val connectionIdWhite = "1234-white"
        val connectionIdBlack = "9999-black"

        val gameIdWhite = gameService.getGameIdFromConnectionId(connectionIdWhite)
        val gameIdBlack = gameService.getGameIdFromConnectionId(connectionIdBlack)

        assertThat(gameIdWhite).isEqualTo("1234")
        assertThat(gameIdBlack).isEqualTo("9999")
    }

    @Test
    fun `convert gameId into connectionId`() {
        val gameId = "1234"

        val connectionIdWhite = gameService.getConnectionIdFromGameId(gameId, Colour.white)
        val connectionIdBlack = gameService.getConnectionIdFromGameId(gameId, Colour.black)

        assertThat(connectionIdWhite).isEqualTo("1234-white")
        assertThat(connectionIdBlack).isEqualTo("1234-black")
    }

    @Test
    fun `gameId and connectionId convert back and forth`() {
        val gameId = "1234"

        val connectionId = gameService.getConnectionIdFromGameId(gameId, Colour.black)
        val newGameId = gameService.getGameIdFromConnectionId(connectionId)

        assertThat(newGameId).isEqualTo(gameId)
    }
}