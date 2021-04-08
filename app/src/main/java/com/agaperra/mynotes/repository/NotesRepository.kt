package com.agaperra.mynotes.repository

import com.agaperra.mynotes.response.NoteResponse


interface NotesRepository {
    open fun readNotes(): List<NoteResponse>
    open fun saveEntity(title: String, create_date: String, edit_date: String, note: String)
    open fun readNote(date: String): NoteResponse
    open fun dropNote(date: String)
    open fun updateNote(title: String, create_date: String,edit_date: String, note: String)
}