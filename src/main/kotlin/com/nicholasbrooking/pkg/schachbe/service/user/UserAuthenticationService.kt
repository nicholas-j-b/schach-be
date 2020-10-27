package com.nicholasbrooking.pkg.schachbe.service.user

import com.nicholasbrooking.pkg.schachbe.service.exception.SchachbeException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service


@Service
class UserAuthenticationService() {
    fun checkUserIsUsername(username: String, exception: SchachbeException) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is AnonymousAuthenticationToken || authentication.name != username) {
            throw exception
        }
    }
}
