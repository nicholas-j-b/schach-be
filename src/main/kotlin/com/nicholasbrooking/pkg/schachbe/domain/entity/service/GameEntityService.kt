package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameStartingPosition
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.domain.model.game.*
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameStartingPositionRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class GameEntityService(
        private val gameRepository: GameRepository
) {
    @Transactional
    fun getGame(gameId: Long): Game {
        return gameRepository.findById(gameId)
    }

    @Transactional
    fun doesGameExist(gameId: Long): Boolean =
            gameRepository.existsById(gameId)

    @Transactional
    fun createGame(createGameDto: CreateGameDto): Game {
        val game = Game(
                gameState = createGameDto.gameState,
                gameUsers = mutableListOf(),
                boardId = createGameDto.boardId,
                gameType = createGameDto.gameType
        )
        return gameRepository.save(game)
    }

    @Transactional
    fun getAllGameInfoDtosBy(gameType: GameType, gameState: GameState): List<GameInfoDto> {
        return gameRepository.findAllByGameTypeAndGameState(gameType, gameState).map { it.toInternalDto() }
    }

    @Transactional
    fun getAllGameInfoDtosBy(gameType: GameType): List<GameInfoDto> {
        return gameRepository.findAllByGameType(gameType).map { it.toInternalDto() }
    }
}