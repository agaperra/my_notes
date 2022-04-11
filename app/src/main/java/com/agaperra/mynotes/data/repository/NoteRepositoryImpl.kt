package com.agaperra.mynotes.data.repository

import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.dao.NoteDao
import com.agaperra.mynotes.data.db.toDomain
import com.agaperra.mynotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun readAllNotes(): Flow<List<Note>> = noteDao.all()

    override fun getNoteByDate(date: String) = noteDao.getDataByCreateDate(date).toDomain()

    override suspend fun dropNote(date: String) {
        noteDao.drop(date)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun updateNote(
        title: String?,
        create_date: String,
        edit_date: String,
        note: String?
    ) {
        noteDao.update(title, create_date, edit_date, note)
    }

    override suspend fun updatePosition(position: Int, position_other: Int ){
        noteDao.updatePosition(position,position_other)
        noteDao.updateNextPosition()
    }

    override suspend fun getCount(): Int = noteDao.getCount()

    override suspend fun getMax(): Int? = noteDao.getMax()
}