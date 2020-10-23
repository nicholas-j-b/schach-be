package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeCannotCreateBoard
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeInvalidState
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeUserAlreadyExists
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class RequestReceiver() {
    inline fun <R> schachfishReceive(block: () -> R): R {
        try {
            return block()
        } catch (e: SchachbeInvalidState) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid state", e)
        } catch (e: SchachbeUserAlreadyExists) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Username already exists", e)
        } catch (e: SchachbeCannotCreateBoard) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot create Board", e)
        }
    }
}