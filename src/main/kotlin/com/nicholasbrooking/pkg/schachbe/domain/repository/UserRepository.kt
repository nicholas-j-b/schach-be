package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
    fun findAllByUsernameIn(username: List<String>): List<User>
    fun existsByUsername(username: String): Boolean
    fun getByUsername(username: String): User
}