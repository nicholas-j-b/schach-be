package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.game.CreateGameDto
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameUserDto
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameRepository
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameUserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class GameEntityService(
        private val gameRepository: GameRepository,
        private val gameUserRepository: GameUserRepository
) {
    @Transactional
    fun createGame(createGameDto: CreateGameDto): Game {
        val game = Game(
                gameState = createGameDto.gameState,
                gameUsers = mutableListOf(),
                boardId = createGameDto.boardId
        )
        return gameRepository.save(game)
    }

    fun createGameUsers(game: Game, gameUsersWithUsesr: Map<GameUserDto, User>) {
        val gameUsers = gameUsersWithUsesr.map {
            GameUser(
                    game = game,
                    username = it.value.username,
                    participationType = it.key.participationType,
                    colour = it.key.colour
            )
        }
        gameUserRepository.saveAll(gameUsers)
    }
}