package com.nicholasbrooking.pkg.schachbe.service.game

import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.BoardEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.GameStartingPositionEntityService
import org.springframework.stereotype.Service

@Service
@ExperimentalStdlibApi
class GameStateService (
        private val gameStartingPositionEntityService: GameStartingPositionEntityService,
        private val boardEntityService: BoardEntityService
) {
    fun addGameStateForUser(username: String, positionName: String, boardStateDto: BoardStateDto): Long {
        val boardState = boardEntityService.boardStateDtoToEntity(boardStateDto)
        return gameStartingPositionEntityService.addGameStartingPosition(username, positionName, boardState)
    }

    fun getGameStateNamesForUser(username: String): List<String> {
        return gameStartingPositionEntityService.getAllGameStartingPositionsForUser(username).map { it.positionName }
    }

    fun getGameState(username: String, positionName: String): BoardStateDto {
        val boardState = gameStartingPositionEntityService.getGameStartingPosition(username, positionName).boardState
        return boardEntityService.boardStateEntityToDto(boardState)
    }
}