package com.nicholasbrooking.pkg.schachbe.client

import com.nicholasbrooking.pkg.schachbe.api.model.BoardId
import com.nicholasbrooking.pkg.schachbe.api.model.BoardStateDto
import com.nicholasbrooking.pkg.schachbe.configuration.AppProps
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.net.http.HttpHeaders

const val BOARD_URL_PREFIX = "/board"
const val GET_ALL_IDS_URL = "/allIds"
const val CREATE_BOARD_FROM_STATE = "/create"

@Service
class BoardClient(
        private val appProps: AppProps
) {
    private val client = WebClient.create(appProps.schachfishUrl)
    private val listOfBoardIdType = object : ParameterizedTypeReference<List<BoardId>>() {}
    private val boardIdType = object : ParameterizedTypeReference<BoardId>() {}
    private val boardStateDtoType = object : ParameterizedTypeReference<BoardStateDto>() {}

    fun getAllIds(): List<BoardId> {
        val response = client.get()
                .uri(BOARD_URL_PREFIX + GET_ALL_IDS_URL)
        val ids = response.retrieve().bodyToMono(listOfBoardIdType).block()
        return ids ?: emptyList()
    }

    fun createBoardFromState(boardStateDto: BoardStateDto): BoardId? {
        val response = client.post()
                .uri(BOARD_URL_PREFIX + CREATE_BOARD_FROM_STATE)
                .bodyValue(boardStateDto)
        val id = response.retrieve().bodyToMono(boardIdType).block()
        return id
    }
}