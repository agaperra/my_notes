package com.agaperra.mynotes.domain.use_case

import com.agaperra.mynotes.domain.repository.NoteRepository
import javax.inject.Inject

class UpdatePosition  @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(position: Int, position_other: Int) =
        noteRepository.updatePosition(position, position_other)


}