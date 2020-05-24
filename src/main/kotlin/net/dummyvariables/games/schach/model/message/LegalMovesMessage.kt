package net.dummyvariables.games.schach.model.message

import net.dummyvariables.games.schach.model.game.Move

data class LegalMovesMessage (
        val moves: List<Move>
)
