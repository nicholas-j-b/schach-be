package com.nicholasbrooking.pkg.schachbe.service.board

import com.nicholasbrooking.pkg.schachbe.api.model.BoardId
import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.client.BoardClient
import com.nicholasbrooking.pkg.schachbe.domain.model.game.GameType
import org.springframework.stereotype.Service

@Service
class BoardService(
        private val boardClient: BoardClient
) {
    fun getAllActiveBoardIds(): List<BoardId> {
        return boardClient.getAllIds()
    }

    fun createBoardStateDto(gameType: GameType): BoardStateDto {
        return BoardStateDto()
    }

    fun createBoardFromState(boardStateDto: BoardStateDto): BoardId? {
        return boardClient.createBoardFromState(boardStateDto)
    }
}