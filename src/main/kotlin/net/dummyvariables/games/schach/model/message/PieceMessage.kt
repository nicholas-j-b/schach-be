package net.dummyvariables.games.schach.model.message

import net.dummyvariables.games.schach.model.game.Piece

data class PieceMessage (
        val pieces: List<Piece>
)