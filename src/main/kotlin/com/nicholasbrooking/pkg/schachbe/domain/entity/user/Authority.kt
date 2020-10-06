package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import com.nicholasbrooking.pkg.schachbe.domain.model.user.UserRole
import javax.persistence.*

@Entity
@Table(name = "authority")
data class Authority(
        @Id
        @Column(nullable = false)
        val userRole: String

//        @ManyToMany(mappedBy = "userRoles")
//        val users: Set<User>
)