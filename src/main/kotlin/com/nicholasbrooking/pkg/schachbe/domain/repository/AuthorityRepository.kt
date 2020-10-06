package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthorityRepository: JpaRepository<Authority, String> {
//    @Query("SELECT a.user_role from Authority a WHERE a.username = :username")
//    fun findAllRolesForUser(username: String): List<String>
}