package com.agaperra.mynotes.domain.use_case

import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.domain.repository.NoteRepository
import javax.inject.Inject


class AddNote @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) =
        noteRepository.insertNote(note)

}