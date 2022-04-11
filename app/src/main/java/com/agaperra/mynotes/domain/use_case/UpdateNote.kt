package com.agaperra.mynotes.domain.use_case

import com.agaperra.mynotes.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNote @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(title: String?, create_date: String, edit_date: String, note: String?) =
        noteRepository.updateNote(title, create_date, edit_date, note)

}