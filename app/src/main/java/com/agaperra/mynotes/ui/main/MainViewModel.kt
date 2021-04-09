package com.agaperra.mynotes.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.agaperra.mynotes.data.Note
import com.agaperra.mynotes.data.NoteDatabase
import com.agaperra.mynotes.data.NoteRepository
import com.agaperra.mynotes.interactor.string.StringInteractor


class MainViewModel(application: Application, private val stringInteractor: StringInteractor) : AndroidViewModel(application) {

     val readAllNote: LiveData<List<Note>>
    private val repository: NoteRepository
    val liveData = MutableLiveData<String>()

    init {
        setText()
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        readAllNote = repository.readAllNotes
    }

    private fun setText() {
        liveData.value = stringInteractor.appName
    }

}
