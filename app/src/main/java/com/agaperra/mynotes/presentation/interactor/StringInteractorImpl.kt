package com.agaperra.mynotes.presentation.interactor

import android.content.Context
import com.agaperra.mynotes.R
import com.agaperra.mynotes.domain.interactor.StringInteractor

class StringInteractorImpl(context: Context) : StringInteractor {
    override val appName: String = context.getString(R.string.app_name)
}