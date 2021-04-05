package com.agaperra.mynotes.utils

import com.agaperra.mynotes.response.NoteResponse

sealed class AppState {
    data class Success(val noteData: List<NoteResponse>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}