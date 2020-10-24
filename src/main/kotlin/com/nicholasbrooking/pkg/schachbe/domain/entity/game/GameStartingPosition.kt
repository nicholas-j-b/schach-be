package com.nicholasbrooking.pkg.schachbe.domain.entity.game

import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import javax.persistence.*

@Entity
class GameStartingPosition (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    val id: Long = 0,

    @Column(nullable = false)
    val creatorUsername: String,

    @Column(nullable = false)
    val positionName: String,

    @ManyToOne
    @JoinColumn(name = "board_state_id")
    val boardState: BoardState
)
