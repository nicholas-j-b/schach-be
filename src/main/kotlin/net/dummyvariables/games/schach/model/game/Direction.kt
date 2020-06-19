package net.dummyvariables.games.schach.model.game

class Direction(
        val x: Int,
        val y: Int
) {
    fun getNextPosition(position: Position): Position? {
        val newPosition = Position(position.x + x, position.y + y)
        return if (isLegal(newPosition)) newPosition else null
    }

    fun getNextPosition(position: Position, number: Int): Position? {
        var pos: Position? = position
        for (i in 0 until number) {
            pos = pos?.let { getNextPosition(it) }
        }
        return pos
    }

    private fun isLegal(position: Position): Boolean {
        return position.x in 0..7 && position.y in 0..7
    }
}
