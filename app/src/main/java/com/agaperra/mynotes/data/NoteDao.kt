package com.agaperra.mynotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY position desc")
    fun all(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE create_date LIKE :date")
    fun getDataByCreateDate(date: String): LiveData<Note>

    @Query("DELETE FROM Note WHERE create_date LIKE :date")
    fun drop(date: String)

    @Query("UPDATE NOTE SET position=:position,title=:title,edit_date=:edit_date,note=:note WHERE create_date=:create_date")
    fun update(position: Int, title: String?, create_date: String, edit_date: String, note: String?)

    @Query("UPDATE NOTE SET position=:position_other WHERE position=:position")
    fun updatePosition(position: Int, position_other: Int )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Note)

    @Query("SELECT COUNT (*) FROM Note")
    fun getCount(): Int

}