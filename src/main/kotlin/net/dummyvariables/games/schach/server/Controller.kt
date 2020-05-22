package net.dummyvariables.games.schach.server

import net.dummyvariables.games.schach.service.ConnectionService
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MessageService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class Controller(
        private val connectionService: ConnectionService,
        private val messageService: MessageService,
        private val gameService: GameService
) {
    @RequestMapping("/test")
    fun test(): String {
        return "hello World"
    }

    @RequestMapping("/board/getConnection")
    fun getWebSocketConnection(): String {
        val gameId = connectionService.getNewGameId()
        val message = gameService.allocateToGame(gameId)
        return messageService.asJson(message)
    }
}