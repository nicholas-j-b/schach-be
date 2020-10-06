package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.BoardApi
import com.nicholasbrooking.pkg.schachbe.api.model.BoardId
import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.api.model.GameType
import com.nicholasbrooking.pkg.schachbe.service.board.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping

@CrossOrigin
@Controller
class BoardController(
        private val boardService: BoardService
): BoardApi {
    private val requestReceiver = RequestReceiver()
    override fun createBoardFromType(gameType: GameType?): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun deleteBoard(boardId: Long?): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun getAllBoardIds(): ResponseEntity<List<BoardId>> {
        requestReceiver.schachfishReceive {
            return ResponseEntity.ok(boardService.getAllActiveBoardIds())
        }
    }

    override fun getBoard(boardId: Long?): ResponseEntity<BoardStateDto> {
        TODO("Not yet implemented")
    }

//        @GetMapping("/board/allIds")
//    fun getAllBoardIds(): ResponseEntity<List<BoardId>> {
//        requestReceiver.schachfishReceive {
//            return ResponseEntity.ok(boardService.getAllActiveBoardIds().map { it })
//        }
//    }
}