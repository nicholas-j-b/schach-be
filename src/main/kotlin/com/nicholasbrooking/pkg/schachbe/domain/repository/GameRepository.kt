package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GameRepository: CrudRepository<Game, String> {
    fun findAllByGameTypeAndGameState(gameType: GameType, gameState: GameState): List<Game>
    fun findAllByGameType(gameType: GameType): List<Game>
}