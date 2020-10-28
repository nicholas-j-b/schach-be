package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import javax.persistence.*

@Entity
@Table(name = "authority")
data class Authority(
        @Id
        @Column(nullable = false)
        val userRole: String
)