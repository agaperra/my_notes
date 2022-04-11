package com.agaperra.mynotes.presentation.di

import com.agaperra.mynotes.data.repository.NoteRepositoryImpl
import com.agaperra.mynotes.domain.interactor.StringInteractor
import com.agaperra.mynotes.domain.repository.NoteRepository
import com.agaperra.mynotes.presentation.interactor.StringInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface BindsModule {

    @Binds
    fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    fun bindStringInteractor(stringInteractorImpl: StringInteractorImpl): StringInteractor

}