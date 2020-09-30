package com.nicholasbrooking.pkg.schachbe.client

import com.nicholasbrooking.pkg.schachbe.configuration.AppProps
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

const val BOARD_URL_PREFIX = "/board"
const val GET_ALL_IDS_URL = "/allIds"

@Service
class BoardClient(
        private val appProps: AppProps
) {
    private val client = WebClient.create(appProps.schachfishUrl)
    private val longListType = object : ParameterizedTypeReference<List<Long>>() {}

    fun getAllIds(): List<Long> {
        val response = client.get()
                .uri(BOARD_URL_PREFIX + GET_ALL_IDS_URL)
        val ids = response.retrieve().bodyToMono(longListType).block()
        return ids ?: emptyList()
    }
}