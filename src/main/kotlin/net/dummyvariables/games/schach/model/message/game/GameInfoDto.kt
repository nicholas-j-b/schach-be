package net.dummyvariables.games.schach.model.message.game

data class GameInfoDto(
        val gameId: String,
        val playerIds: List<String>
)
