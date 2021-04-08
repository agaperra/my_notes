package com.agaperra.mynotes.repository

import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.room.dao.NoteDao
import com.agaperra.mynotes.room.data.Note

class NotesRepositoryImpl(private val localDataSource: NoteDao) : NotesRepository {

    override fun readNotes(): List<NoteResponse> {
        return convertNoteEntityToNote(localDataSource.all())
    }

    override fun saveEntity(title: String, create_date: String, edit_date: String, note: String) {
        localDataSource.insert(convertNoteToEntity(title, create_date, edit_date, note))
    }

    override fun readNote(date: String): NoteResponse {
        return convertNoteToNoteResponse(localDataSource.getDataByHeader(date))
    }

    override fun dropNote(date: String) {
        localDataSource.drop(date)
    }

    override fun updateNote(title: String, create_date: String, edit_date: String, note: String) {
        localDataSource.update(convertNoteToEntity(title, create_date, edit_date, note))
    }

    private fun convertNoteEntityToNote(entityList: List<Note>): List<NoteResponse> =
            entityList.map {
                NoteResponse(
                        it.title,
                        it.create_date,
                        it.edit_date,
                        it.note
                )
            }

    private fun convertNoteToNoteResponse(note: Note): NoteResponse =
            NoteResponse(note.title, note.create_date, note.edit_date, note.note)

    private fun convertNoteToEntity(title: String, create_date: String, edit_date: String, note: String): Note = Note(
            title,
            create_date,
            edit_date,
            note
    )
}
