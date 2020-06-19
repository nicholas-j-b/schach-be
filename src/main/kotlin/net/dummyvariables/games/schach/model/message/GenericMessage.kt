package net.dummyvariables.games.schach.model.message

data class GenericMessage(
        val connectionId: String,
        val channel: String,
        val message: String
)
