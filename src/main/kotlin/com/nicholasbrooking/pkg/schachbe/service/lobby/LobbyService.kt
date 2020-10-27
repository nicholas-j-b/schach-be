package com.nicholasbrooking.pkg.schachbe.service.lobby

import com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour
import com.nicholasbrooking.pkg.schachbe.domain.model.game.*
import com.nicholasbrooking.pkg.schachbe.service.board.BoardService
import com.nicholasbrooking.pkg.schachbe.service.exception.auth.SchachbeUserDisallowed
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeCannotCreateBoard
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidRoleForUser
import com.nicholasbrooking.pkg.schachbe.service.game.GameService
import com.nicholasbrooking.pkg.schachbe.service.user.UserAuthenticationService
import com.nicholasbrooking.pkg.schachbe.service.util.RandomService
import org.springframework.stereotype.Service

@Service
@ExperimentalStdlibApi
class LobbyService(
        private val gameService: GameService,
        private val randomService: RandomService,
        private val boardService: BoardService,
        private val userAuthenticationService: UserAuthenticationService
) {
    fun createGame(gameType: GameType, gameRequestDto: GameRequestDto): Long {
        val boardStateDto = boardService.createBoardStateDto(gameType)
        val boardId = boardService.createBoardFromState(boardStateDto)
                ?: throw SchachbeCannotCreateBoard("Schachfish failed to create board")
        val createGameDto = getCreateGameDto(gameRequestDto, boardId)
        return gameService.createGame(createGameDto)
    }

    fun getCreateGameDto(gameRequestDto: GameRequestDto, boardId: Long): CreateGameDto {
        val challengerColour = randomService.getRandomColour()
        val challengedColour = Colour.getOtherColour(challengerColour)
        val gameUsers = mutableListOf(
                GameUserDto(
                        username = gameRequestDto.challengerUsername,
                        colour = challengerColour,
                        participationType = ParticipationType.PLAYER
                )
        )
        if (gameRequestDto.challengedUsername.isNotEmpty()) {
            gameUsers += GameUserDto(
                    username = gameRequestDto.challengedUsername,
                    colour = challengedColour,
                    participationType = ParticipationType.PLAYER
            )
        }
        return CreateGameDto(
                gameType = gameRequestDto.gameType,
                gameState = gameRequestDto.gameState,
                gameUsers = gameUsers,
                boardId = boardId
        )
    }

    fun getAllGames(gameType: GameType, gameState: GameState): List<GameInfoDto> {
        return gameService.getAllGameInfoDtosBy(gameType, gameState)
    }

    fun getAllGames(gameType: GameType): List<GameInfoDto> {
        return gameService.getAllGameInfoDtosBy(gameType)
    }

    fun joinGame(gameType: GameType, gameId: Long, gameUserDto: GameUserDto) {
        userAuthenticationService.checkUserIsUsername(gameUserDto.username, SchachbeUserDisallowed())
        // set active game of user
        // add gameuser -> add game to user / user to game
    }
}