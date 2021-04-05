package com.agaperra.mynotes

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.agaperra.mynotes.room.NoteDatabase
import com.agaperra.mynotes.room.dao.NoteDao

class App : Application() {

    override fun onCreate() {
        app = this
        super.onCreate()
    }

    companion object {
        private var app: App? = null
        private var db: NoteDatabase? = null
        private const val DB_NAME = "Note.db"

        fun getNoteDao(): NoteDao {
            if (db == null) {
                synchronized(NoteDatabase::class.java) {
                    if (db == null) {
                        if (app == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            app!!.applicationContext,
                            NoteDatabase::class.java,
                            DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.noteDao()
        }
    }


}