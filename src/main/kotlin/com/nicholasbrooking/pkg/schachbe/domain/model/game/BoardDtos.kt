package com.nicholasbrooking.pkg.schachbe.domain.model.game


enum class Colour {
    BLACK, WHITE;
    companion object {
        fun getOtherColour(colour: Colour): Colour = if (colour == WHITE) BLACK else WHITE
    }
}