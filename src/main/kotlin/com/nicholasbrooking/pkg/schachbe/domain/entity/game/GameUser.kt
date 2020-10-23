package com.nicholasbrooking.pkg.schachbe.domain.entity.game

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.Colour
import com.nicholasbrooking.pkg.schachbe.domain.model.game.ParticipationType
import javax.persistence.*

@Entity
@Table(name = "game_user")
class GameUser(
        @Id
        @GeneratedValue
        @Column(nullable = false)
        val id: Long = 0,

        @ManyToOne
        val game: Game,

        @Column(nullable = false, name = "username")
        val username: String,

        @Column(nullable = false)
        val participationType: ParticipationType,

        val colour: Colour
)
