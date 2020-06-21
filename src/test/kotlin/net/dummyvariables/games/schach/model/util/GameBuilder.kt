package net.dummyvariables.games.schach.model.util

import net.dummyvariables.games.schach.model.game.StandardBoard
import net.dummyvariables.games.schach.model.game.Board
import net.dummyvariables.games.schach.service.EntityManagementService

class BoardBuilder {
    companion object {
        fun getBasicBoard(): StandardBoard {
            return StandardBoard(EntityManagementService())
        }

        fun getEmptyBoard(): Board {
            return Board(EntityManagementService())
        }
    }
}