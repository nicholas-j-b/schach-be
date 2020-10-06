package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.mapping.toDatabaseString
import com.nicholasbrooking.pkg.schachbe.domain.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserEntityService(
        private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map { it.toInternalDto() }
    }

    fun appendUserRoles(username: String, userRoles: List<UserRole>) {
        val user = userRepository.getOne(username)
        user.userRoles += userRoles.map { it.toDatabaseString() }
        userRepository.save(user)
    }

    fun replaceUserRoles(username: String, userRoles: List<UserRole>) {
        val user = userRepository.getOne(username)
        user.userRoles = userRoles.mapTo(HashSet()) { it.toDatabaseString() }
        userRepository.save(user)
    }
}