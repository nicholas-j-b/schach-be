package net.dummyvariables.games.schach.model.game

data class Player (
        val connectionId: String,
        val colour: Colour,
        var isTurn: Boolean = false,
        var ready: Boolean = false

)
