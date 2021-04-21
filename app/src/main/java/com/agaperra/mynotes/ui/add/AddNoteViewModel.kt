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

    fun getCount(): Int = repository.getCount()
    fun getDetails(date: String)= repository.getNoteByDate(date)

    fun saveNoteToDB(position: Int, title: String?, create_date: String, edit_date: String, note: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(Note(position, title, create_date, edit_date, note))
        }
    }

    fun updateNote(position: Int, title: String?, create_date: String, edit_date: String, note: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(position, title, create_date, edit_date, note)
        }
    }

    fun updatePosition(position: Int, position_other: Int ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePosition(position, position_other)
        }
    }


}
