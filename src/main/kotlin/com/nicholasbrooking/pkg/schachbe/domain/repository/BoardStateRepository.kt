package com.nicholasbrooking.pkg.schachbe.domain.repository

import com.nicholasbrooking.pkg.schachbe.domain.entity.board.BoardState
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardStateRepository: CrudRepository<BoardState, String> {
}