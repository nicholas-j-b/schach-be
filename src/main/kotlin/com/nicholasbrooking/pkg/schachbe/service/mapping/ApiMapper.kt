package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.api.model.*

fun com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto.toApiDto() = UserDto()
        .username(this.username)
        .roles(this.roles.map { it.toApiEnum() })

fun com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole.toApiEnum(): UserRole {
    return when(this) {
        com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole.ADMIN -> UserRole.ADMIN
        com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole.PLAYER -> UserRole.PLAYER
    }
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.message.SuccessMessage.toApiDto(): MessageDto {
    return MessageDto()
            .message(this.message)
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour.toApiEnum(): Colour {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour.BLACK -> Colour.BLACK
        com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour.WHITE -> Colour.WHITE
    }
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState.toApiEnum(): GameState {
    return when(this) {
        com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState.OPEN -> GameState.OPEN
        com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState.ACTIVE -> GameState.ACTIVE
        com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState.INACTIVE -> GameState.INACTIVE
        com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState.COMPLETE -> GameState.COMPLETE
    }
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.GameInfoDto.toApiDto(): GameInfoDto {
    return GameInfoDto().gameType(this.gameType.toApiEnum())
            .gameState(this.gameState.toApiEnum())
            .participants(this.participants.map { it.toApiDto() })
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.GameUserDto.toApiDto(): GameUserDto {
    return GameUserDto()
            .username(this.username)
            .participationType(this.participationType.toApiEnum())
            .colour(this.colour.toApiEnum())
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.ParticipationType.toApiEnum(): ParticipationType {
    return when(this) {
        com.nicholasbrooking.pkg.schachbe.domain.model.game.ParticipationType.PLAYER -> ParticipationType.PLAYER
        com.nicholasbrooking.pkg.schachbe.domain.model.game.ParticipationType.SPECTATOR -> ParticipationType.SPECTATOR
    }
}

fun com.nicholasbrooking.pkg.schachbe.domain.model.game.GameType.toApiEnum(): GameType {
    return when(this) {
        com.nicholasbrooking.pkg.schachbe.domain.model.game.GameType.STANDARD -> GameType.STANDARD
    }
}
