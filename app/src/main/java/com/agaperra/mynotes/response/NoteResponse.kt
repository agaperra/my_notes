package com.agaperra.mynotes.response

data class NoteResponse(
    val title: String,
    val create_date: String,
    val edit_date: String,
    val note: String?
)