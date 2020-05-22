package net.dummyvariables.games.schach.model.game

class Game() {
    val board: Board = Board()
    val players = mutableListOf<Player>()

    fun addPlayer(connectionId: String, colour: Colour) {
        players.add(Player(connectionId, colour))
    }
}