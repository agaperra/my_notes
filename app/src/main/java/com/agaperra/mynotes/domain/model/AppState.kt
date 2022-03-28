package com.agaperra.mynotes.domain.model

sealed class AppState<out T> {
    class Success<T>(val data: T) : AppState<T>()
    class Error<T>(val error: Throwable) : AppState<T>()
    object Loading: AppState<Nothing>()
}