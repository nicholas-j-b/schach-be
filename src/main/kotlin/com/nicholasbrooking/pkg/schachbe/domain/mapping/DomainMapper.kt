package com.nicholasbrooking.pkg.schachbe.domain.mapping

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
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
        userRoleToInternalEnum(it)
    }
    return UserDto(
            username = this.username,
            roles = userRoles.toList()
    )
}

fun UserRole.toDatabaseString(): String {
    return "ROLE_$this"
}
