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
        private val gameRepository: GameRepository,
        private val gameUserRepository: GameUserRepository,
        private val gameStartingPositionRepository: GameStartingPositionRepository
) {
    @Transactional
    fun getAllGameStartingPositionsForUser(username: String): List<GameStartingPosition> {
        return gameStartingPositionRepository.findAllByCreatorUsername(username)
    }

    @Transactional
    fun getGameStartingPosition(username: String, positionName: String): GameStartingPosition {
        return gameStartingPositionRepository.getByCreatorUsernameAndPositionName(username, positionName)
    }

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
    fun createGameUsers(game: Game, gameUsersWithUsesr: Map<GameUserDto, User>) {
        val gameUsers = gameUsersWithUsesr.map {
            GameUser(
                    game = game,
                    user = it.value,
                    participationType = it.key.participationType,
                    colour = it.key.colour
            )
        }
        gameUserRepository.saveAll(gameUsers)
    }

    @Transactional
    fun addGameStartingPosition(creatorUsername: String, positionName: String, boardState: BoardState): Long {
        val gameStartingPosition = GameStartingPosition(
                creatorUsername = creatorUsername,
                positionName = positionName,
                boardState = boardState
        )
        gameStartingPositionRepository.save(gameStartingPosition)
        return gameStartingPosition.id
    }

    @Transactional
    fun getAllGamesBy(gameType: GameType, gameState: GameState): List<GameInfoDto> {
        return gameRepository.findAllByGameTypeAndGameState(gameType, gameState).map { it.toInternalDto() }
    }

    @Transactional
    fun getAllGamesBy(gameType: GameType): List<GameInfoDto> {
        return gameRepository.findAllByGameType(gameType).map { it.toInternalDto() }
    }
}