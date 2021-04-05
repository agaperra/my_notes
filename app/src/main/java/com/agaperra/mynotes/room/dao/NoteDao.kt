package com.agaperra.mynotes.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.agaperra.mynotes.room.data.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY date desc")
    fun all(): List<Note>

    @Query("SELECT * FROM Note ORDER BY date desc")
    fun all1(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE date LIKE :date")
    fun getDataByHeader(date: String): Note


    @Query("DELETE FROM Note WHERE date LIKE :date")
    fun drop(date: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Note)

    @Update
    fun update(entity: Note)

    @Delete
    fun delete(entity: Note)
}