package net.dummyvariables.games.schach.model.game

import net.dummyvariables.games.schach.model.game.piece.King
import net.dummyvariables.games.schach.service.EntityManagementService
import net.dummyvariables.games.schach.service.MovementService

class StandardBoard(
        entityManagementService: EntityManagementService
) : Board(entityManagementService) {

    init {
        pieceTypes.forEach {cls ->
            Colour.values().forEach { colour ->
                val piece = if (cls == King::class) {
                    cls.constructors.first().call(colour, 0, entityManagementService, MovementService())
                } else {
                    cls.constructors.first().call(colour, 0, entityManagementService)
                }
                addPiece(piece)
                for (id in 1 until piece.startingAmount) {
                    cls.constructors.first().call(colour, id, entityManagementService)
                }
            }
        }
    }

}