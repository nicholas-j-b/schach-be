package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false)
        var enabled: Boolean,

        @ManyToMany
        @JoinTable(
                name = "user_authority",
                joinColumns = [ JoinColumn(name = "username") ],
                inverseJoinColumns = [ JoinColumn(name = "user_role") ]
        )
        var userRoles: Set<Authority>
)