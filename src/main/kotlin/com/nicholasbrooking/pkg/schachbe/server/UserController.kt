package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.HealthApi
import com.nicholasbrooking.pkg.schachbe.api.UserApi
import com.nicholasbrooking.pkg.schachbe.api.model.NewUserDto
import com.nicholasbrooking.pkg.schachbe.api.model.UserDto
import com.nicholasbrooking.pkg.schachbe.api.model.UserRole
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiDto
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiEnum
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalEnum
import com.nicholasbrooking.pkg.schachbe.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin
@Controller
class UserController(
        val userService: UserService
) : UserApi {
    private val requestReceiver = RequestReceiver()

    override fun getUserExists(username: String): ResponseEntity<Boolean> {
        requestReceiver.schachfishReceive {
            return ResponseEntity.ok(userService.getUserExists(username))
        }
    }

    override fun registerNewUser(newUserDto: NewUserDto): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getAllUsers(): ResponseEntity<List<UserDto>> {
        requestReceiver.schachfishReceive {
            return ResponseEntity.ok(userService.getAllUsers().map { it.toApiDto() })
        }
    }

    override fun updateUserRoles(username: String, append: Boolean, userRoles: List<UserRole>): ResponseEntity<String> {
        requestReceiver.schachfishReceive {
            when (append) {
                true -> userService.appendUserRoles(username, userRoles.map { it.toInternalEnum() })
                false -> userService.replaceUserRoles(username, userRoles.map { it.toInternalEnum() })
            }
            return ResponseEntity.ok("success")
        }
    }
}
