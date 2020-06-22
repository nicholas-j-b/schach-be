package net.dummyvariables.games.schach.model.game.piece

import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.model.game.Colour
import net.dummyvariables.games.schach.model.game.Position
import net.dummyvariables.games.schach.model.message.legalMoves.MoveCollectionDto
import net.dummyvariables.games.schach.model.message.legalMoves.MoveDestinationDto
import net.dummyvariables.games.schach.service.EntityManagementService

class Queen(
        override val colour: Colour,
        override val id: Int,
        override val entityManagementService: EntityManagementService
) : Piece() {
    override val pieceName = "queen"
    override val startingAmount = 1
    override var position: Position = if (colour == Colour.black) Position(3, 0) else Position(3, 7)
    override fun move(to: Position, board: Board) {
        position = to
    }

    override fun getLegalMoves(): MoveCollectionDto {
        return MoveCollectionDto(position, (RookDirections.directions + BishopDirections.directions).flatMap {
            getLegalPositionsRay(position, it)
        }.map {
            MoveDestinationDto(it)
        })
    }
}
