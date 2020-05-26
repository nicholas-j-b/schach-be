package net.dummyvariables.games.schach.model.piece

object IdUtil {
    private var id = 0

    fun getNextId(): Int {
        val num = id
        id += 1
        return num
    }
}