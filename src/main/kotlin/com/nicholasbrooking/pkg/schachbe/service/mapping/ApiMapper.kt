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



