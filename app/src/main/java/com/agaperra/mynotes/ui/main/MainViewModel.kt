package com.agaperra.mynotes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agaperra.mynotes.App.Companion.getNoteDao
import com.agaperra.mynotes.interactor.string.StringInteractor
import com.agaperra.mynotes.repository.NotesRepository
import com.agaperra.mynotes.repository.NotesRepositoryImpl
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