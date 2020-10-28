package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour
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

fun com.nicholasbrooking.pkg.schachbe.api.model.GameState.toInternalEnum(): GameState {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.api.model.GameState.COMPLETE -> GameState.COMPLETE
        com.nicholasbrooking.pkg.schachbe.api.model.GameState.OPEN -> GameState.OPEN
        com.nicholasbrooking.pkg.schachbe.api.model.GameState.ACTIVE -> GameState.ACTIVE
        com.nicholasbrooking.pkg.schachbe.api.model.GameState.INACTIVE -> GameState.INACTIVE
    }
}

fun com.nicholasbrooking.pkg.schachbe.api.model.Colour.toInternalEnum(): Colour {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.api.model.Colour.BLACK -> Colour.BLACK
        com.nicholasbrooking.pkg.schachbe.api.model.Colour.WHITE -> Colour.WHITE
    }
}

fun com.nicholasbrooking.pkg.schachbe.api.model.GameUserDto.toInternalDto(gameId: Long): GameUserDto {
    return GameUserDto(
            username = this.username,
            gameId = gameId,
            participationType = this.participationType.toInternalEnum(),
            colour = this.colour.toInternalEnum()
    )
}

fun com.nicholasbrooking.pkg.schachbe.api.model.GameUserDto.toInternalRequestDto(gameId: Long): GameUserRequestDto {
    return GameUserRequestDto(
            username = this.username,
            participationType = this.participationType.toInternalEnum(),
            colour = this.colour.toInternalEnum()
    )
}

fun com.nicholasbrooking.pkg.schachbe.api.model.ParticipationType.toInternalEnum(): ParticipationType {
    return when(this) {
        com.nicholasbrooking.pkg.schachbe.api.model.ParticipationType.PLAYER -> ParticipationType.PLAYER
        com.nicholasbrooking.pkg.schachbe.api.model.ParticipationType.SPECTATOR -> ParticipationType.SPECTATOR
    }
}
