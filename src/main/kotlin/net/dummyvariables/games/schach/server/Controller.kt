package net.dummyvariables.games.schach.server

import net.dummyvariables.games.schach.service.ConnectionService
import net.dummyvariables.games.schach.service.GameService
import net.dummyvariables.games.schach.service.MessageService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class Controller(
        private val connectionService: ConnectionService,
        private val messageService: MessageService,
        private val gameService: GameService
) {
    @RequestMapping("/lobby/games")
    fun getGames(): String {
        val message = gameService.getAllGamesAsDtos()
        return messageService.asJson(message)
    }

    @RequestMapping("/game/create")
    fun createGame(): String {
        val gameId = connectionService.getNewGameId()
        val message = gameService.createGameAndAllocate(gameId)
        return messageService.asJson(message)
    }

    @RequestMapping("/game/join/{gameId}")
    fun joinGame(@PathVariable("gameId") gameId: String): String {
        val message = gameService.allocateToExistingGame(gameId)
        return messageService.asJson(message)
    }

    @RequestMapping("/game/spectate")
    fun spectateGame(): String {
        TODO("not yet implemented")
    }
}