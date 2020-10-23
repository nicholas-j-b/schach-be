package com.nicholasbrooking.pkg.schachbe.domain.entity.game

import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameState
import javax.persistence.*

@Entity
@Table(name = "game")
class Game(
        @Id
        @GeneratedValue
        @Column(nullable = false)
        val id: Long = 0,

        @Column(nullable = false)
        var gameState: GameState,

        @OneToMany
        val gameUsers: MutableList<GameUser>,

        @Column(nullable = false)
        val boardId: Long
)
