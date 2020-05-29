package net.dummyvariables.games.schach.model.game

data class Position(
        val x: Int,
        val y: Int
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Position) {
            return x == other.x && y == other.y
        } else {
            super.equals(other)
        }
    }
}
