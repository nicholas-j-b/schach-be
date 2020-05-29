package net.dummyvariables.games.schach.service

import com.google.gson.Gson
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDto
import org.springframework.stereotype.Service

@Service
class MessageService {
    private val gson = Gson()

    fun asJson(obj: Any): String {
        return gson.toJson(obj)
    }

    fun moveDtoFromJson(msg: String): MoveDto {
        return gson.fromJson(msg, MoveDto::class.java)
    }
}