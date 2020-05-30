package net.dummyvariables.games.schach.model.game

data class SquareOccupancyType(
        val isOccupied: Boolean,
        val isSquare: Boolean,
        val colour: Colour?
)
