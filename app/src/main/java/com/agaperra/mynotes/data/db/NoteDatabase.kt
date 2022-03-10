package com.agaperra.mynotes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.agaperra.mynotes.data.db.dao.NoteDao
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.migrations.Migrations

@Database(entities = [Note::class], version = 9, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addMigrations(Migrations.MigrationFrom8To9)

                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}