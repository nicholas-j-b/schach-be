package com.nicholasbrooking.pkg.schachbe.domain.entity.service

import com.nicholasbrooking.pkg.schachbe.api.model.*
import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import com.nicholasbrooking.pkg.schachbe.domain.entity.serialise.BoardStateSeder
import com.nicholasbrooking.pkg.schachbe.domain.repository.BoardStateRepository
import com.nicholasbrooking.pkg.schachbe.service.mapping.toApiEnum
import com.nicholasbrooking.pkg.schachbe.service.mapping.toInternalEnum
import org.springframework.stereotype.Service


@ExperimentalStdlibApi
@Service
class BoardEntityService(
        private val boardStateRepository: BoardStateRepository,
        private val boardStateSeder: BoardStateSeder
) {

    fun boardStateDtoToEntity(boardStateDto: BoardStateDto): BoardState {
        val boardState = BoardState(
                whiteStatusDto = boardStateSeder.colourStatusDtoToDatabaseBlob(boardStateDto.whiteStatus),
                blackStatusDto = boardStateSeder.colourStatusDtoToDatabaseBlob(boardStateDto.blackStatus),
                turn = boardStateDto.turn.toInternalEnum(),
                history = boardStateSeder.historyToDatabaseBlob(boardStateDto.history)
        )
        boardStateRepository.save(boardState)
        return boardState
    }

    fun boardStateEntityToDto(boardState: BoardState): BoardStateDto {
        return BoardStateDto()
                .whiteStatus(boardStateSeder.bytesToColourStatusDto(boardState.whiteStatusDto))
                .blackStatus(boardStateSeder.bytesToColourStatusDto(boardState.blackStatusDto))
                .turn(boardState.turn.toApiEnum())
                .history(boardStateSeder.bytesToHistory(boardState.history))
    }

}



