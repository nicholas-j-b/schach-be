package net.dummyvariables.games.schach.service

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.GameCollection
import net.dummyvariables.games.schach.model.message.InitialMessage
import org.springframework.stereotype.Service

@Service
class GameService {
    fun allocateToGame(gameId: String): InitialMessage {
        //TODO("at the moment a new game is created every time with a player assigned to white")
        val game = GameCollection.addGame(gameId)
        val colour = Colour.white
        val connectionId = getConnectionIdFromGameId(gameId, colour)
        game.addPlayer(connectionId, colour)
        return InitialMessage(gameId, colour, connectionId)
    }

    fun getBoard(connectionId: String): Board {
        val gameId = getGameIdFromConnectionId(connectionId)
        return GameCollection.games[gameId]?.board ?: throw Exception("no game found")
    }

    fun getGameIdFromConnectionId(connectionId: String) = connectionId.split("-").first()

    fun getConnectionIdFromGameId(gameId: String, colour: Colour) = "$gameId-$colour"
}