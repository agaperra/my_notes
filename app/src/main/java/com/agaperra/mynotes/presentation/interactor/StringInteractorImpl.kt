package com.agaperra.mynotes.presentation.interactor

import android.content.Context
import com.agaperra.mynotes.R
import com.agaperra.mynotes.domain.interactor.StringInteractor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringInteractorImpl @Inject constructor(
    @ApplicationContext private val context: Context) : StringInteractor {
    override val appName: String = context.getString(R.string.app_name)
}