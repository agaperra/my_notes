package com.agaperra.mynotes.domain.use_case

import javax.inject.Inject

class NoteCases @Inject constructor(
    val addNote: AddNote,
    val dropNote: DropNote,
    val readAll: ReadAllNotes
)