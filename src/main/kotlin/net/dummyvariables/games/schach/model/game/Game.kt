package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.message.game.GameInfoDto
import net.dummyvariables.games.schach.service.EntityManagementService

class Game(
        val entityManagementService: EntityManagementService
) {
    val board: StandardBoard = StandardBoard(entityManagementService)
    val players = mutableListOf<Player>()

    fun getActivePlayer(): Player {
        return players.first { it.isTurn }
    }

    fun getNonActivePlayer(): Player {
        return players.first { !it.isTurn }
    }

    fun nextTurn() {
        val currentActivePlayer = getActivePlayer()
        val currentNonActivePlayer = getNonActivePlayer()
        currentActivePlayer.isTurn = false
        currentNonActivePlayer.isTurn = true
    }

    fun addPlayer(connectionId: String, colour: Colour) {
        players.add(Player(connectionId, colour))
    }

    fun toDto(gameId: String): GameInfoDto {
        return GameInfoDto(gameId, players.map { it.connectionId })
    }

    fun getNonAssignedColour(): Colour {
        return if (players.first().colour == Colour.black) Colour.white else Colour.black
    }

    fun registerPlayerReady(connectionId: String) {
        players.first { player -> player.connectionId == connectionId }.ready = true
    }

    fun areBothPlayersReady(): Boolean {
        return players.getOrNull(0)?.ready ?: false && players.getOrNull(1)?.ready ?: false
    }
}