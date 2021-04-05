package com.agaperra.mynotes.repository

import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.room.dao.NoteDao
import com.agaperra.mynotes.room.data.Note

class NotesRepositoryImpl(private val localDataSource: NoteDao) : NotesRepository {

    override fun readNotes(): List<NoteResponse> {
        return convertNoteEntityToNote(localDataSource.all())
    }

    override fun saveEntity(title: String, date: String, note: String) {
        localDataSource.insert(convertNoteToEntity(title, date, note))
    }

    override fun readNote(date: String): NoteResponse {
        return convertNoteToNoteResponse(localDataSource.getDataByHeader(date))
    }

    override fun dropNote(date: String) {
        localDataSource.drop(date)
    }

    private fun convertNoteEntityToNote(entityList: List<Note>): List<NoteResponse> =
        entityList.map {
            NoteResponse(
                it.title,
                it.date,
                it.note
            )
        }

    private fun convertNoteToNoteResponse(note: Note): NoteResponse =
        NoteResponse(note.title, note.date, note.note)

    private fun convertNoteToEntity(title: String, date: String, note: String): Note = Note(
        title,
        date,
        note
    )
}
