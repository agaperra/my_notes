package com.agaperra.mynotes.domain.use_case

import javax.inject.Inject

data class NoteCases @Inject constructor(
    val addNote: AddNote,
    val dropNote: DropNote,
    val readAll: ReadAllNotes,
    val updateNote: UpdateNote,
    val updatePosition: UpdatePosition
)