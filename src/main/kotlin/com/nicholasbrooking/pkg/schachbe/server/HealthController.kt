package com.nicholasbrooking.pkg.schachbe.server

import com.nicholasbrooking.pkg.schachbe.api.HealthApi
import com.nicholasbrooking.pkg.schachbe.api.model.MessageDto
import com.nicholasbrooking.pkg.schachbe.domain.model.message.SuccessMessage
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiDto

@CrossOrigin
@Controller
class HealthController(): HealthApi {
    private val requestReceiver = RequestReceiver()

    override fun amIAlive(): ResponseEntity<MessageDto> {
        return ResponseEntity.ok(SuccessMessage().toApiDto())
    }
}
