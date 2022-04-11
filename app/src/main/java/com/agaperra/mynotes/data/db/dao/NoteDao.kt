package com.agaperra.mynotes.data.db.dao

import androidx.room.*
import com.agaperra.mynotes.data.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY position asc")
    fun all(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE create_date LIKE :date")
    fun getDataByCreateDate(date: String): Flow<Note>

    @Query("DELETE FROM Note WHERE create_date LIKE :date")
    suspend fun drop(date: String)

    @Query("UPDATE NOTE SET title=:title,edit_date=:edit_date,note=:note WHERE create_date=:create_date")
    suspend fun update(title: String?, create_date: String, edit_date: String, note: String?)

    @Query( "update NOTE \n" +
            "    set position = (case when position =:position_ then -:position_other else -:position_ end)\n" +
            "    where position in (:position_, :position_other)")
    fun updatePosition(position_: Int, position_other: Int)

    @Query( "update NOTE \n" +
            "            set position = -position \n" +
            "           where position < 0")
    fun updateNextPosition()

    @Query("SELECT MAX(position) FROM Note")
    suspend fun getMax(): kotlin.Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Note)

    @Query("SELECT COUNT (*) FROM Note ")
    suspend fun getCount(): Int

}