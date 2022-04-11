package com.agaperra.mynotes.presentation.adapters.listeners

import com.agaperra.mynotes.domain.model.NoteItem

interface OnItemClickListener {
    fun onItemClick(note: NoteItem)
}