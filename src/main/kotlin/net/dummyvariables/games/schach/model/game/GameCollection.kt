package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.service.EntityManagementService

object GameCollection {
    val games = mutableMapOf<String, Game>()

    fun addGame(gameId: String): Game {
        if (!games.containsKey(gameId)) {
            games[gameId] = Game(EntityManagementService())
        }
        return games[gameId] ?: throw Exception("game doesn't exist")
    }
}