package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.api.model.GameType
import org.springframework.core.convert.converter.Converter

class ApiStringToEnumConverter: Converter<String, GameType>{
    override fun convert(source: String): GameType {
        return GameType.fromValue(source)
    }
}