package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.service.exception.SchachbeInvalidState
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class RequestReceiver() {
    inline fun <R> schachfishReceive(block: () -> R): R {
        try {
            return block()
        } catch (e: SchachbeInvalidState) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid state", e)
        }
    }
}