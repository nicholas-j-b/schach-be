package com.nicholasbrooking.pkg.schachbe.domain.mapping

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.Game
import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameInfoDto
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameUserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidRoleForUser

fun userRoleToInternalEnum(userRole: String): UserRole {
    return when (userRole) {
        "ROLE_ADMIN" -> UserRole.ADMIN
        "ROLE_PLAYER" -> UserRole.PLAYER
        else -> throw SchachbeInvalidRoleForUser("role type not found")
    }
}

fun User.toInternalDto(): UserDto {
    val userRoles = this.userRoles.map {
        userRoleToInternalEnum(it.userRole)
    }
    return UserDto(
            username = this.username,
            roles = userRoles.toList()
    )
}

fun UserRole.toDatabaseString(): String {
    return "ROLE_$this"
}

fun Game.toInternalDto(): GameInfoDto {
    return GameInfoDto(
            gameType = this.gameType,
            gameState = this.gameState,
            participants = this.gameUsers.map { it.toInternalDto() }
    )
}

fun GameUser.toInternalDto(): GameUserDto {
    return GameUserDto(
            username = this.user.username,
            participationType = this.participationType,
            colour = this.colour
    )
}