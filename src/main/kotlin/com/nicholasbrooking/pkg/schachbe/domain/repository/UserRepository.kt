package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}