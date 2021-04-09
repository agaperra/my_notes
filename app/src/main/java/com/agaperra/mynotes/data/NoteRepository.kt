package com.agaperra.mynotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllNotes: LiveData<List<Note>> = noteDao.all()

    fun getNoteByDate(date: String): LiveData<Note> = noteDao.getDataByCreateDate(date)

    fun dropNote(date: String) {
        noteDao.drop(date)
    }

    fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    fun updateNote(note: Note){
        noteDao.update(note)
    }
}