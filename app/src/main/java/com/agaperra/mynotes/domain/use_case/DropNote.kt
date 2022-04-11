package com.agaperra.mynotes.domain.use_case

import com.agaperra.mynotes.domain.repository.NoteRepository
import javax.inject.Inject

class DropNote @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(date: String) =
        noteRepository.dropNote(date)

}