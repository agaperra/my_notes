package com.agaperra.mynotes.presentation.ui.main

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Index
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.NoteDatabase
import com.agaperra.mynotes.data.repository.NoteRepositoryImpl
import com.agaperra.mynotes.domain.interactor.StringInteractor
import com.agaperra.mynotes.domain.model.AppState
import com.agaperra.mynotes.domain.model.NoteItem
import com.agaperra.mynotes.domain.use_case.NoteCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val stringInteractor: StringInteractor,
    private val noteCases: NoteCases
) : ViewModel() {

    val liveData = MutableLiveData<String>()

    val cases = noteCases

    private val _readAllNote = MutableStateFlow<List<NoteItem>>(listOf())
    var readAllNote = _readAllNote.asStateFlow()

    init {
        setText()
        readData()
    }

    private fun readData() = noteCases.readAll().onEach { resource ->
        _readAllNote.value = resource
    }.launchIn(viewModelScope)



    private fun setText() {
        liveData.value = stringInteractor.appName
    }

}
