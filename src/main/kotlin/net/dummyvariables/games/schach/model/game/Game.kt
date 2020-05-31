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
}