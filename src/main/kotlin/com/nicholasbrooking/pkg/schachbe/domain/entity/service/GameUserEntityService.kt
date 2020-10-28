package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.domain.model.game.*
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameUserRepository
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidState
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class GameUserEntityService(
        private val gameUserRepository: GameUserRepository
) {
    @Transactional
    fun createGameUsers(game: Game, gameUserRequestDto: GameUserRequestDto, user: User) {
        val gameUser = GameUser(
                game = game,
                user = user,
                participationType = gameUserRequestDto.participationType,
                colour = gameUserRequestDto.colour
        )
        user.gameUsers += gameUser
        gameUserRepository.save(gameUser)
    }

    @Transactional
    fun deleteGameUser(gameId: Long, user: User) {
        user.gameUsers = user.gameUsers.filterNot {
            it.game.id == gameId
        }
    }

    @Transactional
    fun isUserInGame(username: String, gameId: Long): Boolean {
        val numGamesForUser = gameUserRepository.findAllByGameId(gameId).count {
            it.user.username == username
        }
        return when (numGamesForUser) {
            0 -> false
            1 -> true
            else -> throw SchachbeInvalidState("User $username in game more than once")
        }
    }

    @Transactional
    fun getAllGameUsersForGame(gameId: Long): List<GameUserDto> =
            gameUserRepository.findAllByGameId(gameId).map { it.toInternalDto() }
}
