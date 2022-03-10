package com.agaperra.mynotes.data.repository

import androidx.lifecycle.LiveData
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.dao.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val readAllNotes: LiveData<List<Note>> = noteDao.all()

    fun getNoteByDate(date: String): LiveData<Note> = noteDao.getDataByCreateDate(date)

    fun dropNote(date: String) {
        noteDao.drop(date)
    }

    fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    fun updateNote(title: String?, create_date: String, edit_date: String, note: String?){
        noteDao.update(title, create_date, edit_date, note)
    }

//    fun updatePosition(position: Int, position_other: Int ){
//        noteDao.updatePosition(position,position_other)
//    }

    fun getCount(): Int=noteDao.getCount()
}