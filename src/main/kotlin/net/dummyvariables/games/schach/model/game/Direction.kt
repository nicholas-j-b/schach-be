package net.dummyvariables.games.schach.model.game

class Direction(
        val x: Int,
        val y: Int
) {
    fun getNextPosition(position: Position): Position {
        return Position(position.x + x, position.y + y)
    }
}
