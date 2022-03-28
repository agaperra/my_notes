package com.agaperra.mynotes.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agaperra.mynotes.domain.interactor.StringInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SplashViewModel @Inject constructor(private val stringInteractor: StringInteractor): ViewModel() {

    val liveData = MutableLiveData<String>()

    init {
        setText()
    }

    private fun setText() {
        liveData.value = stringInteractor.appName
    }
}