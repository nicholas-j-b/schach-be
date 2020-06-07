package net.dummyvariables.games.schach.model.game

class Direction(
        val x: Int,
        val y: Int
) {
    fun getNextPosition(position: Position): Position? {
        val newPosition = Position(position.x + x, position.y + y)
        return if (isLegal(newPosition)) newPosition else null
    }

    private fun isLegal(position: Position): Boolean {
        return position.x in 0..7 && position.y in 0..7
    }
}
