package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.domain.model.Colour
import com.nicholasbrooking.pkg.schachbe.domain.model.user.*
import com.nicholasbrooking.pkg.schachbe.domain.model.game.*


fun com.nicholasbrooking.pkg.schachbe.api.model.UserRole.toInternalEnum(): UserRole {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.api.model.UserRole.ADMIN -> UserRole.ADMIN
        com.nicholasbrooking.pkg.schachbe.api.model.UserRole.PLAYER -> UserRole.PLAYER
    }
}

fun com.nicholasbrooking.pkg.schachbe.api.model.NewUserDto.toInternalDto(): NewUserDto {
    return NewUserDto(
            username = this.username,
            password = this.password,
            userRoles = mutableListOf(),
            enabled = true
    )
}

fun com.nicholasbrooking.pkg.schachbe.api.model.CreateGameDto.toInternalDto(): GameRequestDto{
    return GameRequestDto(
            gameState = GameState.OPEN,
            gameType = this.gameType.toInternalEnum(),
            challengerUsername = this.challengerUsername,
            challengedUsername = this.challengedUsername
    )
}

fun com.nicholasbrooking.pkg.schachbe.api.model.GameType.toInternalEnum(): GameType {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.api.model.GameType.STANDARD -> GameType.STANDARD
    }
}
