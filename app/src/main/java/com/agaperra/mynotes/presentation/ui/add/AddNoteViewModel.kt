package com.agaperra.mynotes.presentation.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.NoteDatabase
import com.agaperra.mynotes.data.repository.NoteRepositoryImpl
import com.agaperra.mynotes.domain.model.AppState
import com.agaperra.mynotes.domain.model.NoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class AddNoteViewModel @Inject constructor(
    private val repositoryImpl: NoteRepositoryImpl) : ViewModel() {


    private val _detailsNote = MutableStateFlow<AppState<NoteItem>>(AppState.Loading)
    var detailsNote = _detailsNote.asStateFlow()



    fun getDetails(date: String) {
        repositoryImpl.getNoteByDate(date).onEach {
            _detailsNote.value = AppState.Success(data = it)
        }.launchIn(viewModelScope)
    }

    fun saveNoteToDB(title: String?, create_date: String, edit_date: String, note: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val max = repositoryImpl.getMax() ?: 0
            repositoryImpl.insertNote(Note(0,max + 1 ,title, create_date, edit_date, note))
        }
    }

    fun updateNote(title: String?, create_date: String, edit_date: String, note: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.updateNote(title, create_date, edit_date, note)
        }
    }


}
