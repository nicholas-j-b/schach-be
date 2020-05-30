package net.dummyvariables.games.schach.service

import org.springframework.stereotype.Service


@Service
class ConnectionService {
    companion object ActiveConnections {
        val ids = mutableListOf<Int>()
        private var topId: Int = 0

        fun getNewId(): Int {
            ids.add(topId)
            return topId++
        }
    }

    fun getExistingId(): String {
        return ids.first().toString()
    }

    fun getNewGameId(): String {
        return getNewId().toString()
    }
}