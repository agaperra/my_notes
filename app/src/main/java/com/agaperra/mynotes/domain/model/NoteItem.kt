package com.agaperra.mynotes.domain.model

data class NoteItem(
    var position: Int,
    var title: String?,
    var edit_date: String,
    var create_date: String,
    var note: String?
)