package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.service.board.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BoardController(
        private val boardService: BoardService
) {
    private val requestReceiver = RequestReceiver()

    @GetMapping("/board/allIds")
    fun getAllBoardIds(): ResponseEntity<List<String>> {
        requestReceiver.schachfishReceive {
            return ResponseEntity.ok(boardService.getAllActiveBoardIds().map { it.toString() })
        }
    }

}