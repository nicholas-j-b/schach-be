package com.nicholasbrooking.pkg.schachbe.service.user

import com.nicholasbrooking.pkg.schachbe.domain.entity.service.UserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userEntityService: UserEntityService,
        private val userRepository: UserRepository,
        private val authorityService: AuthorityService
) {
    fun getUserExists(username: String): Boolean {
        return userRepository.existsById(username)
    }

    fun getAllUsers(): List<UserDto> {
        return userEntityService.getAllUsers()
    }

    fun appendUserRoles(username: String, userRoles: List<UserRole>){
        userEntityService.appendUserRoles(username, userRoles)
    }

    fun replaceUserRoles(username: String, userRoles: List<UserRole>){
        userEntityService.replaceUserRoles(username, userRoles)
    }

}


