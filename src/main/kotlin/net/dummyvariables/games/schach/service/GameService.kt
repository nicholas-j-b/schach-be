package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Game
import net.dummyvariables.games.schach.model.game.GameCollection
import net.dummyvariables.games.schach.model.message.InitialMessage
import net.dummyvariables.games.schach.model.message.game.GameInfoDto
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class GameService {
    fun getAllGamesAsDtos(): List<GameInfoDto> {
        return GameCollection.games.map {
            it.value.toDto(it.key)
        }
    }
    fun createGameAndAllocate(gameId: String): InitialMessage {
        val game = GameCollection.addGame(gameId)
//        val colour = if (Random.nextBoolean()) Colour.black else Colour.white
        val colour = Colour.white
        return allocatePlayerToGame(gameId, game, colour)
    }

    fun allocateToExistingGame(gameId: String): InitialMessage {
        val game = GameCollection.games[gameId] ?: throw Exception("no game found")
        val colour = game.getFreeColour()
        return allocatePlayerToGame(gameId, game, colour)
    }

    private fun allocatePlayerToGame(gameId: String, game: Game, colour: Colour): InitialMessage {
        val connectionId = getConnectionIdFromGameId(gameId, colour)
        game.addPlayer(connectionId, colour)
        return InitialMessage(gameId, colour, connectionId)
    }

    fun getBoard(connectionId: String): Board {
        val gameId = getGameIdFromConnectionId(connectionId)
        return GameCollection.games[gameId]?.board ?: throw Exception("no game found")
    }

    fun waitingOnColour(board: Board): Colour {
        return board.currentColour
    }

    fun setNextColourTurn(board: Board): Colour {
        return board.newTurn()
    }

    fun getWhiteConnectionId(connectionId: String) = getOppositeConnectionId(Colour.black, connectionId)

    fun getBlackConnectionId(connectionId: String) = getOppositeConnectionId(Colour.white, connectionId)

    fun getOppositeConnectionId(colour: Colour, connectionId: String): String {
        val gameId = getGameIdFromConnectionId(connectionId)
        val otherColour = Colour.getOtherColour(colour)
        return getConnectionIdFromGameId(gameId, otherColour)
    }

    fun getGameFromConnectionId(connectionId: String) =
            GameCollection.games[getGameIdFromConnectionId(connectionId)] ?: throw Exception("no game found")

    fun getColourFromConnectionId(connectionId: String) = connectionId.split("-")[1]

    fun getGameIdFromConnectionId(connectionId: String) = connectionId.split("-").first()

    fun getConnectionIdFromGameId(gameId: String, colour: Colour) = "$gameId-$colour"
}