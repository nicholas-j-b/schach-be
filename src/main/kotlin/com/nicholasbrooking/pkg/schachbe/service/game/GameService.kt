package com.nicholasbrooking.pkg.schachbe.service.game

import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.BoardEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.GameEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.UserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.model.game.CreateGameDto
import org.springframework.stereotype.Service

@Service
@ExperimentalStdlibApi
class GameService (
        private val gameEntityService: GameEntityService,
        private val userEntityService: UserEntityService,
        private val boardEntityService: BoardEntityService
) {
    fun createGame(createGameDto: CreateGameDto) {
        val game = gameEntityService.createGame(createGameDto)
        val gameUsersWithUser = createGameDto.gameUsers.map { it to userEntityService.getByUsername(it.username)}.toMap()
        gameEntityService.createGameUsers(game, gameUsersWithUser)
    }

    fun addGameStateForUser(username: String, positionName: String, boardStateDto: BoardStateDto): Long {
        val boardState = boardEntityService.boardStateDtoToEntity(boardStateDto)
        return gameEntityService.addGameStartingPosition(username, positionName, boardState)
    }

    fun getGameStateNamesForUser(username: String): List<String> {
        return gameEntityService.getAllGameStartingPositionsForUser(username).map { it.positionName }
    }

    fun getGameState(username: String, positionName: String): BoardStateDto {
        val boardState = gameEntityService.getGameStartingPosition(username, positionName).boardState
        return boardEntityService.boardStateEntityToDto(boardState)
    }

}