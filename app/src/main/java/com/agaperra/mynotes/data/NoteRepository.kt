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

    fun updateNote(position: Int, title: String?, create_date: String, edit_date: String, note: String?){
        noteDao.update(position,title, create_date, edit_date, note)
    }

    fun updatePosition(position: Int, position_other: Int ){
        noteDao.updatePosition(position,position_other)
    }

    fun getCount(): Int=noteDao.getCount()
}