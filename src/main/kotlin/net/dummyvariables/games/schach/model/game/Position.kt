package net.dummyvariables.games.schach.model.game

data class Position(
        val x: Int,
        val y: Int
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Position) {
            x == other.x && y == other.y
        } else {
            throw error("comparing piece to $other")
        }
    }
}
