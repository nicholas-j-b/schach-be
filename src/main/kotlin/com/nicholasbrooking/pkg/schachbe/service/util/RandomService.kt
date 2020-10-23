package com.nicholasbrooking.pkg.schachbe.service.util

import com.nicholasbrooking.pkg.schachbe.domain.model.Colour
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomService {
    private val random = Random(1)

    fun getRandomColour(): Colour {
        val colourIndex = random.nextInt(Colour.values().size)
        return Colour.values()[colourIndex]
    }
}