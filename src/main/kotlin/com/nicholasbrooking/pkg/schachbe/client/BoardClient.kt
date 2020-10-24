package com.nicholasbrooking.pkg.schachbe.client

import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.configuration.AppProps
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

const val BOARD_URL_PREFIX = "/board"
const val GET_ALL_IDS_URL = "/allIds"
const val CREATE_BOARD_FROM_STATE = "/create"

@Service
class BoardClient(
        private val appProps: AppProps
) {
    private val client = WebClient.create(appProps.schachfishUrl)
    private val listOfLongType = object : ParameterizedTypeReference<List<Long>>() {}
    private val longType = object : ParameterizedTypeReference<Long>() {}
//    private val boardStateDtoType = object : ParameterizedTypeReference<BoardStateDto>() {}

    fun getAllIds(): List<Long> {
        val response = client.get()
                .uri(BOARD_URL_PREFIX + GET_ALL_IDS_URL)
        val ids = response.retrieve().bodyToMono(listOfLongType).block()
        return ids ?: emptyList()
    }

    fun createBoardFromState(boardStateDto: BoardStateDto): Long? {
        val response = client.post()
                .uri(BOARD_URL_PREFIX + CREATE_BOARD_FROM_STATE)
                .bodyValue(boardStateDto)
        return response.retrieve().bodyToMono(longType).block()
    }
}