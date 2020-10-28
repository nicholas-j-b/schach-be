package com.nicholasbrooking.pkg.schachbe.domain.model.game


enum class GameState {
    ACTIVE, INACTIVE, OPEN, COMPLETE
}

enum class GameType {
    STANDARD
}

data class CreateGameDto(
        val gameState: GameState,
        val gameType: GameType,
        val gameUsers: MutableList<GameUserRequestDto>,
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

data class GameUserRequestDto(
        val username: String,
        val participationType: ParticipationType,
        val colour: Colour
)

data class GameUserDto(
        val username: String,
        val gameId: Long,
        val participationType: ParticipationType,
        val colour: Colour
)

data class GameInfoDto(
        val gameId: Long,
        val gameType: GameType,
        val participants: List<GameUserDto>,
        val gameState: GameState
)