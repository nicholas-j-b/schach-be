package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Game
import net.dummyvariables.games.schach.model.game.GameCollection
import net.dummyvariables.games.schach.model.message.InitialMessage
import net.dummyvariables.games.schach.model.message.game.GameInfoDto
import org.springframework.stereotype.Service

@Service
class GameService {
    fun getAllGamesAsDtos(): List<GameInfoDto> {
        return GameCollection.games.map {
            it.value.toDto(it.key)
        }
    }
    fun createGameAndAllocate(gameId: String): InitialMessage {
        val game = GameCollection.addGame(gameId)
        return allocatePlayerToGame(gameId, game, Colour.white)
    }

    fun allocateToExistingGame(gameId: String): InitialMessage {
        val game = GameCollection.games[gameId] ?: throw Exception("no game found")
        return allocatePlayerToGame(gameId, game, Colour.black)
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

    fun getColourFromConnectionId(connectionId: String) = connectionId.split("-")[1]

    fun getGameIdFromConnectionId(connectionId: String) = connectionId.split("-").first()

    fun getConnectionIdFromGameId(gameId: String, colour: Colour) = "$gameId-$colour"
}