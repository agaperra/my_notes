package com.agaperra.mynotes.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.agaperra.mynotes.App
import com.agaperra.mynotes.App.Companion.getNoteDao
import com.agaperra.mynotes.interactor.string.StringInteractor
import com.agaperra.mynotes.repository.NotesRepository
import com.agaperra.mynotes.repository.NotesRepositoryImpl
import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.room.NoteDatabase
import com.agaperra.mynotes.room.data.Note
import com.agaperra.mynotes.utils.AppState

class MainViewModel(
    val noteLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val notesRepository: NotesRepository = NotesRepositoryImpl(getNoteDao()),
) : ViewModel() {
    val liveData = MutableLiveData<String>()
    lateinit var strInt: StringInteractor

    fun setInteractor(stringInteractor: StringInteractor) {
        strInt = stringInteractor
    }

    fun setText() {
        liveData.value = strInt.appName
    }

    fun getAllNotesList() {
        noteLiveData.value = AppState.Loading
        noteLiveData.value = AppState.Success(notesRepository.readNotes())
    }


}