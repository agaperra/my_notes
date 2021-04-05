package com.agaperra.mynotes.ui.add

import androidx.lifecycle.ViewModel
import com.agaperra.mynotes.App
import com.agaperra.mynotes.repository.NotesRepository
import com.agaperra.mynotes.repository.NotesRepositoryImpl
import com.agaperra.mynotes.response.NoteResponse

class AddNoteViewModel(
    private val noteRepository: NotesRepository = NotesRepositoryImpl(App.getNoteDao())
) : ViewModel() {

    lateinit var noteDetails: NoteResponse

    fun saveNoteToDB(title: String, date: String, note: String) {
        noteRepository.saveEntity(title, date, note)
    }

    fun getDetails(date: String) {
        noteDetails = noteRepository.readNote(date)
    }

    fun dropNote(date: String) {
        noteRepository.dropNote(date)
    }
}