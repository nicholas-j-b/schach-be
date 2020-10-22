package com.nicholasbrooking.pkg.schachbe.service.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class SecurityService (
) {
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    fun encryptPassword(password: String): String {
        return bCryptPasswordEncoder.encode(password)
    }
}
