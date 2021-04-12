package com.agaperra.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY create_date desc")
    fun all(): LiveData<List<Note>>

//    @Query("SELECT * FROM Note ORDER BY position desc")
//    fun allPosition(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE create_date LIKE :date")
    fun getDataByCreateDate(date: String):  LiveData<Note>

    @Query("DELETE FROM Note WHERE create_date LIKE :date")
    fun drop(date: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Note)

    @Update
    fun update(entity: Note)

}