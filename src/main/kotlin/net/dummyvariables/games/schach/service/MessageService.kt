package net.dummyvariables.games.schach.service

import com.google.gson.Gson
import org.springframework.stereotype.Service

@Service
class MessageService {
    private val gson = Gson()

    fun asJson(obj: Any): String {
        return gson.toJson(obj)
    }
}