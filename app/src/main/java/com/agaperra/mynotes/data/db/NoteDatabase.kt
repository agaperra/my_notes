package com.agaperra.mynotes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agaperra.mynotes.data.db.dao.NoteDao
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.migrations.Migrations

@Database(entities = [Note::class], version = 12, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}