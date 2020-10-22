package com.nicholasbrooking.pkg.schachbe.domain.model.user

data class UserDto(
        val username: String,
        val roles: List<UserRole>
)

enum class UserRole {
    ADMIN, PLAYER
}

data class NewUserDto(
        val username: String,
        var password: String,
        val enabled: Boolean,
        val userRoles: MutableList<UserRole>
)