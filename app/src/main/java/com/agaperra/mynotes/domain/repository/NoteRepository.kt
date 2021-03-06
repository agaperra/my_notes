package com.agaperra.mynotes.domain.repository

import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.domain.model.NoteItem
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun readAllNotes(): Flow<List<Note>>

    fun getNoteByDate(date: String): Flow<NoteItem>

    suspend fun dropNote(date: String)

    suspend fun insertNote(note: Note)

    suspend fun updateNote(title: String?, create_date: String, edit_date: String, note: String?)

    suspend fun updatePosition(position: Int, position_other: Int)

    suspend fun getCount(): Int

    suspend fun getMax(): Int?
}