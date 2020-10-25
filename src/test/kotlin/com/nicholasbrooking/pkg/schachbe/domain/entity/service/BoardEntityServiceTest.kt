package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import com.nicholasbrooking.pkg.schachbe.domain.model.util.BoardStateGenerator
import com.nicholasbrooking.pkg.schachbe.domain.repository.BoardStateRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@ExperimentalStdlibApi
@SpringBootTest
internal class BoardEntityServiceTest(
        @Autowired private val boardEntityService: BoardEntityService
) {
    @MockBean
    private lateinit var boardStateRepository: BoardStateRepository

    @Test
    fun `serialise full board state`() {
        Mockito.`when`(boardStateRepository.save(any(BoardState::class.java))).thenReturn(null)

        val boardStateDto = BoardStateGenerator.getDefaultBoardStateDto()

        val boardState = boardEntityService.boardStateDtoToEntity(boardStateDto)

        Mockito.verify(boardStateRepository, times(1)).save(any())
        assertThat(boardState.id).isNotNull()
        assertThat(boardState.blackStatusDto).isNotNull
        assertThat(boardState.whiteStatusDto).isNotNull
        assertThat(boardState.turn).isNotNull()
        assertThat(boardState.history).isNotNull
    }

    @Test
    fun `serialise and deserialise full board state`() {
        Mockito.`when`(boardStateRepository.save(any(BoardState::class.java))).thenReturn(null)

        val boardStateDto = BoardStateGenerator.getDefaultBoardStateDto()
        val boardState = boardEntityService.boardStateDtoToEntity(boardStateDto)

        val deserialisedBoardStateDto = boardEntityService.boardStateEntityToDto(boardState)

        Mockito.verify(boardStateRepository, times(1)).save(any())
        assertThat(boardStateDto).isEqualTo(deserialisedBoardStateDto)
    }

    @Test
    fun `serialise and deserialise board state with missing properties`() {
        Mockito.`when`(boardStateRepository.save(any(BoardState::class.java))).thenReturn(null)

        val boardStateDto = BoardStateGenerator.getBoardStateDtoWithMissingFields()
        val boardState = boardEntityService.boardStateDtoToEntity(boardStateDto)

        val deserialisedBoardStateDto = boardEntityService.boardStateEntityToDto(boardState)

        Mockito.verify(boardStateRepository, times(1)).save(any())
        assertThat(boardStateDto).isEqualTo(deserialisedBoardStateDto)
    }
}