package net.dummyvariables.games.schach.model.message.legalMoves

import net.dummyvariables.games.schach.model.game.Position

data class MoveDto (
        val from: Position,
        val to: Position
)
