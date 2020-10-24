package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.GameApi
import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.service.game.GameService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@Controller
@ExperimentalStdlibApi
class GameController(
        private val gameService: GameService
): GameApi {
    private val requestReceiver = RequestReceiver()
    override fun addGameStateForUser(username: String, positionName: String, boardStateDto: BoardStateDto): ResponseEntity<Long> {
        requestReceiver.schachfishReceive {
            val gameStateId = gameService.addGameStateForUser(username, positionName, boardStateDto)
            return ResponseEntity.ok(gameStateId)
        }
    }

    override fun getGameStatesForUser(username: Long): ResponseEntity<List<BoardStateDto>> {
        requestReceiver.schachfishReceive {
            return ResponseEntity.ok(emptyList())
        }
    }
}