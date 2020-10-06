package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.BoardApi
import com.nicholasbrooking.pkg.schachbe.api.HealthApi
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
class HealthController(): HealthApi {
    private val requestReceiver = RequestReceiver()

    override fun amIAlive(): ResponseEntity<String> {
        return ResponseEntity.ok("Yes")
    }
}
