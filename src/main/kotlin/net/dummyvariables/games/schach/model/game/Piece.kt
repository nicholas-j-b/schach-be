package net.dummyvariables.games.schach.model.game

data class Piece(
        val colour: String,
        val pieceName: String,
        val id: Int,
        val position: Position
)
