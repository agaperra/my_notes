package com.agaperra.mynotes.repository

import androidx.lifecycle.LiveData
import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.room.dao.NoteDao
import com.agaperra.mynotes.room.data.Note


interface NotesRepository {
    open fun readNotes(): List<NoteResponse>
    open fun saveEntity(title: String, date: String, note: String)
    open fun readNote(date: String): NoteResponse
    open fun dropNote(date: String)
}