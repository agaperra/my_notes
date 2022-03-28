package com.agaperra.mynotes.presentation.di

import android.content.Context
import androidx.room.Room
import com.agaperra.mynotes.data.db.NoteDatabase
import com.agaperra.mynotes.data.db.dao.NoteDao
import com.agaperra.mynotes.data.db.migrations.Migrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase = Room
        .databaseBuilder(context, NoteDatabase::class.java, "note_database")
        .addMigrations(Migrations.MigrationFrom8To9)
        .build()

    @Singleton
    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()


}