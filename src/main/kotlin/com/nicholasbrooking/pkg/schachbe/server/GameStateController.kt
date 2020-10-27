package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.GameStateApi
import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.service.game.GameService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@Controller
@ExperimentalStdlibApi
class GameStateController(
        private val gameService: GameService
): GameStateApi {
    private val requestReceiver = RequestReceiver()
    override fun addGameStateForUser(username: String, positionName: String, boardStateDto: BoardStateDto): ResponseEntity<Long> {
        requestReceiver.schachbeReceive {
            val gameStateId = gameService.addGameStateForUser(username, positionName, boardStateDto)
            return ResponseEntity.ok(gameStateId)
        }
    }

    override fun getGameStateNamesForUser(username: String): ResponseEntity<List<String>> {
        requestReceiver.schachbeReceive {
            val gameStateNames = gameService.getGameStateNamesForUser(username)
            return ResponseEntity.ok(gameStateNames)
        }
    }

    override fun getGameStatesForUser(username: String, positionName: String): ResponseEntity<BoardStateDto> {
        requestReceiver.schachbeReceive {
            val gameStateNames = gameService.getGameState(username, positionName)
            return ResponseEntity.ok(gameStateNames)
        }
    }
}