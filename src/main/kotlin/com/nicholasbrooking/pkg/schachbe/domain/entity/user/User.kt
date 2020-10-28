package com.nicholasbrooking.pkg.schachbe.domain.entity.user

import com.nicholasbrooking.pkg.schachbe.domain.entity.game.GameUser
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue
        @Column(nullable = false)
        val id: Long = 0,

        @Column(nullable = false)
        val username: String,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false)
        var enabled: Boolean,

        @ManyToMany
        @JoinTable(
                name = "user_authority",
                joinColumns = [ JoinColumn(name = "user_id") ],
                inverseJoinColumns = [ JoinColumn(name = "user_role") ]
        )
        var userRoles: Set<Authority>,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
        var gameUsers: List<GameUser>
)