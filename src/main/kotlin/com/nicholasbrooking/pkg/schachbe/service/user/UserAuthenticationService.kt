package com.nicholasbrooking.pkg.schachbe.service.user

import com.nicholasbrooking.pkg.schachbe.service.exception.SchachbeException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.reflect.KClass


@Service
class UserAuthenticationService {
    fun <T: SchachbeException> checkUserIsUsername(username: String, exception: KClass<T>) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is AnonymousAuthenticationToken || authentication.name != username) {
            throw exception.constructors.first().call("Action not permitted for user $username")
        }
    }
}
