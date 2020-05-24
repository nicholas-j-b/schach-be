package net.dummyvariables.games.schach.model.message

import net.dummyvariables.games.schach.model.game.piece.Piece

data class PieceMessage (
        val pieces: List<Piece>
)