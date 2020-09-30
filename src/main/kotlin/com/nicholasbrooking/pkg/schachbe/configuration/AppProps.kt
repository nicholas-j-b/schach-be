package com.nicholasbrooking.pkg.schachbe.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class AppProps {
    @Value("\${schachfish.url}")
    lateinit var schachfishUrl: String
}