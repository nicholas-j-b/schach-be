package com.nicholasbrooking.pkg.schachbe.server


import com.nicholasbrooking.pkg.schachbe.api.LobbyApi
import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.service.lobby.LobbyService
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiDto
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalEnum
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*

@Controller
@CrossOrigin
@ExperimentalStdlibApi
class LobbyController(
        private val lobbyService: LobbyService
) : LobbyApi {
    private val requestReceiver = RequestReceiver()

    override fun createGame(gameType: GameType, createGameDto: CreateGameDto): ResponseEntity<Long> {
        requestReceiver.schachbeReceive {
            val gameId = lobbyService.createGame(gameType.toInternalEnum(), createGameDto.toInternalDto())
            return ResponseEntity.ok(gameId)
        }
    }

    override fun getAllGamesInLobby(gameType: GameType, gameState: Optional<GameState>): ResponseEntity<List<GameInfoDto>> {
        requestReceiver.schachbeReceive {
            val allGames = if (gameState.isPresent) {
                lobbyService.getAllGames(gameType.toInternalEnum(), gameState.get().toInternalEnum())
            } else {
                lobbyService.getAllGames(gameType.toInternalEnum())
            }
            return ResponseEntity.ok(allGames.map { it.toApiDto() })
        }
    }

    override fun joinGame(gameType: GameType, gameId: Long, gameUserDto: GameUserDto): ResponseEntity<String> {
        requestReceiver.schachbeReceive {
            lobbyService.joinGame(gameType.toInternalEnum(), gameId, gameUserDto.toInternalDto())
            return ResponseEntity.ok("Success")
        }
    }
}
