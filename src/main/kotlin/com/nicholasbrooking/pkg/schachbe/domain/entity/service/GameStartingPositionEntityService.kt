package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameStartingPosition
import com.nicholasbrooking.pkg.schachbe.domain.repository.GameStartingPositionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class GameStartingPositionEntityService(
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
    fun addGameStartingPosition(creatorUsername: String, positionName: String, boardState: BoardState): Long {
        val gameStartingPosition = GameStartingPosition(
                creatorUsername = creatorUsername,
                positionName = positionName,
                boardState = boardState
        )
        gameStartingPositionRepository.save(gameStartingPosition)
        return gameStartingPosition.id
    }
}