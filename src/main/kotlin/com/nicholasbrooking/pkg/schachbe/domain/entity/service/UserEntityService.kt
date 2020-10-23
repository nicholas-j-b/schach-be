package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.Authority
import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.mapping.toDatabaseString
import com.nicholasbrooking.pkg.schachbe.domain.mapping.toInternalDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.NewUserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserDto
import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import com.nicholasbrooking.pkg.schachbe.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserEntityService(
        private val userRepository: UserRepository
) {
    fun getByUsername(username: String): User {
        return userRepository.getOne(username)
    }

    fun getByUsernames(usernames: List<String>): List<User> {
        return userRepository.findAllById(usernames)
    }

    fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map { it.toInternalDto() }
    }

    fun appendUserRoles(username: String, userRoles: List<UserRole>) {
        val user = userRepository.getOne(username)
        user.userRoles += userRoles.map { Authority(it.toDatabaseString()) }
        userRepository.save(user)
    }

    fun replaceUserRoles(username: String, userRoles: List<UserRole>) {
        val user = userRepository.getOne(username)
        user.userRoles = userRoles.mapTo(HashSet()) { Authority(it.toDatabaseString()) }
        userRepository.save(user)
    }

    fun createNewUser(newUserDto: NewUserDto) {
        val user = User(
                username = newUserDto.username,
                password = newUserDto.password,
                enabled = newUserDto.enabled,
                userRoles = newUserDto.userRoles.mapTo(HashSet()) { Authority(it.toDatabaseString()) }
        )
        userRepository.save(user)
    }
}