package com.nicholasbrooking.pkg.schachbe.domain.entity.game

import com.nicholasbrooking.pkg.schachbe.domain.entity.user.User
import com.nicholasbrooking.pkg.schachbe.domain.model.game.Colour
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
        @JoinColumn(name = "game_id")
        val game: Game,

        @OneToOne @MapsId
        val user: User,

        @Column(nullable = false)
        val participationType: ParticipationType,

        val colour: Colour
)
