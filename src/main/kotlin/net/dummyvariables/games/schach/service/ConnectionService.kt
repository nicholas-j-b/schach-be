package net.dummyvariables.games.schach.service

import org.springframework.stereotype.Service


@Service
class ConnectionService {
    companion object ActiveConnections {
        val ids = mutableListOf<String>()
        private var topId: Int = 0

        fun getNewId(): Int {
            return topId++
        }
    }

    fun getNewGameId(): String {
        return getNewId().toString()
    }
}