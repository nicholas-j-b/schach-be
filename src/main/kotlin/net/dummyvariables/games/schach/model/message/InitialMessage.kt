package net.dummyvariables.games.schach.model.message

import net.dummyvariables.games.schach.model.game.Colour

data class InitialMessage(
        val gameId: String,
        val colour: Colour,
        val connectionId: String
)
