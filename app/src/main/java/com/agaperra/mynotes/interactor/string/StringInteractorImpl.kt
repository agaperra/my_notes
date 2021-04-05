package com.agaperra.mynotes.interactor.string

import android.content.Context
import com.agaperra.mynotes.R

class StringInteractorImpl(context: Context) : StringInteractor {
    override val appName: String = context.getString(R.string.app_name)
}