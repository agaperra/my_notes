package com.agaperra.mynotes.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agaperra.mynotes.interactor.string.StringInteractor

class MainViewModel(private val stringInteractor: StringInteractor) : ViewModel() {
    val liveData = MutableLiveData<String>()

    init {
        setText()
    }
    private fun setText() {
        liveData.value = stringInteractor.appName
    }
}