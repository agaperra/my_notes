package com.agaperra.mynotes.domain.use_case

import com.agaperra.mynotes.data.db.toDomain
import com.agaperra.mynotes.data.repository.NoteRepositoryImpl
import javax.inject.Inject

class ReadAllNotes @Inject constructor(private val noteRepository: NoteRepositoryImpl) {

    operator fun invoke() = noteRepository.readAllNotes().toDomain()

}