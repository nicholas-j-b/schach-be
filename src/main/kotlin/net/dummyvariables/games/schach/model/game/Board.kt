package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.*
import net.dummyvariables.games.schach.service.EntityManagementService

class Board(
        entityManagementService: EntityManagementService
) : EmptyBoard(entityManagementService) {

    init {
        pieceTypes.forEach {cls ->
            Colour.values().forEach { colour ->
                val piece = cls.call(colour, 0, entityManagementService)
                addPiece(piece)
                for (id in 1 until piece.startingAmount) {
                    addPiece(cls.call(colour, id, entityManagementService))
                }
            }
        }
    }

}