package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.domain.model.user.*


fun com.nicholasbrooking.pkg.schachbe.api.model.UserRole.toInternalEnum(): UserRole {
    return when (this) {
        com.nicholasbrooking.pkg.schachbe.api.model.UserRole.ADMIN -> UserRole.ADMIN
        com.nicholasbrooking.pkg.schachbe.api.model.UserRole.PLAYER -> UserRole.PLAYER
    }
}