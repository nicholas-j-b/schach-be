package com.nicholasbrooking.pkg.schachbe.service.game

import com.nicholasbrooking.pkg.schachbe.domain.entity.service.GameEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.GameUserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.UserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.model.game.*
import com.nicholasbrooking.pkg.schachbe.service.exception.auth.SchachbeUserDisallowed
import org.springframework.stereotype.Service

@Service
@ExperimentalStdlibApi
class GameService (
        private val gameEntityService: GameEntityService,
        private val gameUserEntityService: GameUserEntityService,
        private val userEntityService: UserEntityService
) {
    fun createGame(createGameDto: CreateGameDto): Long {
        val game = gameEntityService.createGame(createGameDto)
        createGameDto.gameUsers.forEach{
            joinGame(game.id, it)
        }
        return game.id
    }

    fun joinGame(gameId: Long, gameUserRequestDto: GameUserRequestDto) {
        val game = gameEntityService.getGame(gameId)
        val user = userEntityService.getByUsername(gameUserRequestDto.username)
        gameUserEntityService.createGameUsers(game, gameUserRequestDto, user)
    }

    fun getAllGameInfoDtosBy(gameType: GameType, gameState: GameState): List<GameInfoDto> {
        return gameEntityService.getAllGameInfoDtosBy(gameType, gameState)
    }

    fun getAllGameInfoDtosBy(gameType: GameType): List<GameInfoDto> {
        return gameEntityService.getAllGameInfoDtosBy(gameType)
    }

    fun authoriseGameCreate(gameRequestDto: GameRequestDto) {
        val usernamesInChallenge = listOf(gameRequestDto.challengerUsername, gameRequestDto.challengedUsername)
        if (userEntityService.getByUsernames(usernamesInChallenge).size != usernamesInChallenge.size) {
            throw SchachbeUserDisallowed("A chosen User does not exist")
        }
    }

    fun authoriseGameJoin(gameId: Long, gameUserRequestDto: GameUserRequestDto) {
        if (!gameEntityService.doesGameExist(gameId)) {
            throw SchachbeUserDisallowed("Game does not exist")
        }
        if (gameUserEntityService.isUserInGame(gameUserRequestDto.username, gameId)) {
            throw SchachbeUserDisallowed("User not permitted to join game more than once")
        }
        val authorised = when (gameUserRequestDto.participationType) {
            ParticipationType.SPECTATOR -> true
            ParticipationType.PLAYER -> authoriseJoinAsPlayer(gameId)
        }
        if (!authorised) {
            throw SchachbeUserDisallowed("User not permitted to join game")
        }
    }

    private fun authoriseJoinAsPlayer(gameId: Long): Boolean {
        val existingGameUsers = gameUserEntityService.getAllGameUsersForGame(gameId)
        val numPlayersInGame = existingGameUsers.filter {
            it.participationType == ParticipationType.PLAYER
        }.size
        return numPlayersInGame < 2
    }
}