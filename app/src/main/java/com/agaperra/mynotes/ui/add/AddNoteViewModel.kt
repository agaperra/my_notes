package com.agaperra.mynotes.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.mynotes.data.Note
import com.agaperra.mynotes.data.NoteDatabase
import com.agaperra.mynotes.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }

    fun getDetails(date: String)= repository.getNoteByDate(date)

    fun saveNoteToDB(title: String, create_date: String, edit_date: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(Note(title, create_date, edit_date, note))
        }
    }

    fun updateNote(title: String, create_date: String, edit_date: String, note: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(Note(title, create_date, edit_date, note))
        }
    }


}
