package com.nicholasbrooking.pkg.schachbe.domain.model.message

open class MessageDto(
        val message: String
)

class SuccessMessage: MessageDto(
        message = "Success"
)