package net.dummyvariables.games.schach.model.message.pieces

import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position

data class PieceDto (
        val colour: Colour,
        val id: Int,
        val pieceName: String,
        var position: Position
)