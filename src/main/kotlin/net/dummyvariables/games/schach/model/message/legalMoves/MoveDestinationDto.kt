package net.dummyvariables.games.schach.model.message.legalMoves

import net.dummyvariables.games.schach.model.game.Position

data class MoveDestinationDto(
        val to: Position,
        val isPawnPromotion: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        return if (other is MoveDestinationDto) {
            to == other.to
        } else {
            throw error("comparing MoveDestinationDto to $other")
        }
    }
}
