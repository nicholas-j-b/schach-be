package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.message.game.GameInfoDto
import net.dummyvariables.games.schach.service.EntityManagementService

class Game(
        val entityManagementService: EntityManagementService
) {
    val board: Board = Board(entityManagementService)
    val players = mutableListOf<Player>()

    fun addPlayer(connectionId: String, colour: Colour) {
        players.add(Player(connectionId, colour))
    }

    fun toDto(gameId: String): GameInfoDto {
        return GameInfoDto(gameId, players.map { it.connectionId })
    }

    fun getFreeColour(): Colour {
        return if (players.first().colour == Colour.black) Colour.white else Colour.black
    }

    fun registerPlayerReady(connectionId: String) {
        players.first { player -> player.connectionId == connectionId }.ready = true
    }

    fun areBothPlayersReady(): Boolean {
        return players.getOrNull(0)?.ready ?: false && players.getOrNull(1)?.ready ?: false
    }
}