package com.nicholasbrooking.pkg.schachbe.domain.model.game

import com.nicholasbrooking.pkg.schachbe.domain.model.Colour


enum class GameState {
    ACTIVE, INACTIVE, OPEN, COMPLETE
}

enum class GameType {
    STANDARD
}

data class CreateGameDto(
        val gameState: GameState,
        val gameType: GameType,
        val gameUsers: MutableList<GameUserDto>,
        val boardId: Long
)

data class GameRequestDto (
    val gameState: GameState,
    val gameType: GameType,
    val challengerUsername: String,
    val challengedUsername: String
)

enum class ParticipationType{
    PLAYER, SPECTATOR
}

data class GameUserDto(
        val username: String,
        val participationType: ParticipationType,
        val colour: Colour
)

data class GameInfoDto(
        val gameType: GameType,
        val participants: List<GameUserDto>,
        val gameState: GameState
)