package com.agaperra.mynotes.response

data class NoteResponse(
    val title: String,
    val date: String,
    val note: String?
)