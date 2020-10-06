package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import javax.persistence.*

@Entity
@Table(name = "USER")
data class User(
        @Id
        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false)
        var enabled: Boolean,

        @OneToMany(mappedBy = "authority")
        var userRoles: Set<String>
)