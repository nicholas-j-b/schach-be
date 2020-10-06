package com.nicholasbrooking.pkg.schachbe.service.board

import com.nicholasbrooking.pkg.schachbe.api.model.BoardId
import com.nicholasbrooking.pkg.schachbe.client.BoardClient
import org.springframework.stereotype.Service

@Service
class BoardService(
        private val boardClient: BoardClient
) {
    fun getAllActiveBoardIds(): List<BoardId> {
        return boardClient.getAllIds()
    }
}