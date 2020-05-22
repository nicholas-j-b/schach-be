package net.dummyvariables.games.schach.model.game

object GameCollection {
    val games = mutableMapOf<String, Game>()

    fun addGame(gameId: String): Game {
        if (!games.containsKey(gameId)) {
            games[gameId] = Game()
        }
        return games[gameId] ?: throw Exception("game doesn't exist")
    }
}