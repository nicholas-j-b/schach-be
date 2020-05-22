package net.dummyvariables.games.schach.server.game

import net.dummyvariables.games.schach.model.message.PieceMessage
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MessageService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class WebSocketController(
        private val messageService: MessageService,
        private val gameService: GameService
) {
    @MessageMapping("/{connectionId}/initial")
    @SendTo("/down/{connectionId}/initial")
    fun initial(@DestinationVariable connectionId: String, @Payload msg: String): String {
        println(msg)
        println(connectionId)
        Thread.sleep(1000)
        val pieces = gameService.getBoard(connectionId).pieces
        return messageService.asJson(PieceMessage(pieces))
    }

    @MessageMapping("/{connectionId}")
    @SendTo("/down/{connectionId}")
    fun sendMessage(@DestinationVariable connectionId: String, @Payload msg: String): String {
        println(msg)
        println(connectionId)
        Thread.sleep(1000)
        return "placeholder qqq" //messageService.getTestJson()
    }

}