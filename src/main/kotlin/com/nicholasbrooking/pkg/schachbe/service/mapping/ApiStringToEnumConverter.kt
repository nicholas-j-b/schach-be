package com.nicholasbrooking.pkg.schachbe.service.mapping

import com.nicholasbrooking.pkg.schachbe.api.model.GameType

class ApiStringToEnumConverter: org.springframework.core.convert.converter.Converter<String, GameType>{
    override fun convert(source: String): GameType {
        return GameType.fromValue(source)
    }
}