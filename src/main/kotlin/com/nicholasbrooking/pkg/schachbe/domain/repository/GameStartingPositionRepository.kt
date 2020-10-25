package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameStartingPosition
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GameStartingPositionRepository: CrudRepository<GameStartingPosition, String> {
    fun findAllByCreatorUsername(creatorUsername: String): List<GameStartingPosition>
    fun getByCreatorUsernameAndPositionName(creatorUsername: String, positionName: String): GameStartingPosition
}