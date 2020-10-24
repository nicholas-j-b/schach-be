package com.nicholasbrooking.pkg.schachbe.server


import com.nicholasbrooking.pkg.schachbe.api.LobbyApi
import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.service.lobby.LobbyService
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalEnum
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@Controller
@CrossOrigin
@ExperimentalStdlibApi
class LobbyController(
        private val lobbyService: LobbyService
) : LobbyApi {
    private val requestReceiver = RequestReceiver()

    override fun createGame(gameType: GameType, createGameDto: CreateGameDto): ResponseEntity<Boolean> {
        requestReceiver.schachfishReceive {
            lobbyService.createGame(gameType.toInternalEnum(), createGameDto.toInternalDto())
            return ResponseEntity.ok(true)
        }
    }

    override fun getAllGamesInLobby(gameType: GameType?): ResponseEntity<MutableList<GameInfoDto>> {
        TODO("Not yet implemented")
    }

    override fun joinGame(gameType: GameType?, gameId: Long?, joinGameDto: JoinGameDto?): ResponseEntity<MutableList<GameInfoDto>> {
        TODO("Not yet implemented")
    }
}
