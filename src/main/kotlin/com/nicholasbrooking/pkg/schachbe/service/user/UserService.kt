package com.nicholasbrooking.pkg.schachbe.service.user

import com.nicholasbrooking.pkg.schachbe.domain.entity.service.UserEntityService
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.NewUserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.domain.repository.UserRepository
import com.nicholasbrooking.pkg.schachbe.service.exception.data.SchachbeUserAlreadyExists
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

    fun addUser(newUserDto: NewUserDto) {
        if (getUserExists(newUserDto.username)) {
            throw SchachbeUserAlreadyExists("Username: ${newUserDto.username} already exists")
        }
        newUserDto.userRoles += UserRole.PLAYER
        userEntityService.createNewUser(newUserDto)
    }

    fun appendUserRoles(username: String, userRoles: List<UserRole>){
        userEntityService.appendUserRoles(username, userRoles)
    }

    fun replaceUserRoles(username: String, userRoles: List<UserRole>){
        userEntityService.replaceUserRoles(username, userRoles)
    }

}


