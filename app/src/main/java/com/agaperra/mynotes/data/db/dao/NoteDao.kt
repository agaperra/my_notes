package com.agaperra.mynotes.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.agaperra.mynotes.data.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY edit_date desc")
    fun all(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE edit_date LIKE :date")
    fun getDataByCreateDate(date: String): Flow<Note>

    @Query("DELETE FROM Note WHERE edit_date LIKE :date")
    suspend fun drop(date: String)

    @Query("UPDATE NOTE SET title=:title,edit_date=:edit_date,note=:note WHERE create_date=:create_date AND edit_date=:edit_date")
    suspend fun update(title: String?, create_date: String, edit_date: String, note: String?)

//    @Query("UPDATE NOTE SET position=:position_other WHERE position=:position")
//    fun updatePosition(position: Int, position_other: Int )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Note)

    @Query("SELECT COUNT (*) FROM Note")
    suspend fun getCount(): Int

}