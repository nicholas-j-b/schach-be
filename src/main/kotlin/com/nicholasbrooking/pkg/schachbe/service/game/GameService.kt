package com.nicholasbrooking.pkg.schachbe.service.game

import com.nicholasbrooking.pkg.schachbe.domain.entity.service.GameEntityService
import com.nicholasbrooking.pkg.schachbe.domain.entity.service.UserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.model.game.CreateGameDto
import org.springframework.stereotype.Service

@Service
class GameService(
        private val gameEntityService: GameEntityService,
        private val userEntityService: UserEntityService
) {
    fun createGame(createGameDto: CreateGameDto) {
        val game = gameEntityService.createGame(createGameDto)
        val gameUsersWithUser = createGameDto.gameUsers.map { it to userEntityService.getByUsername(it.username)}.toMap()
        gameEntityService.createGameUsers(game, gameUsersWithUser)
    }

}