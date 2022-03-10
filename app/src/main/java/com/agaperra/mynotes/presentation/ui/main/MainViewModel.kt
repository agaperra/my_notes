package com.agaperra.mynotes.presentation.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.NoteDatabase
import com.agaperra.mynotes.data.repository.NoteRepository
import com.agaperra.mynotes.domain.interactor.StringInteractor


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
