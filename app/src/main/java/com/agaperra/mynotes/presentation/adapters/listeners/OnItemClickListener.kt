package com.agaperra.mynotes.presentation.adapters.listeners

import com.agaperra.mynotes.data.db.entity.Note

interface OnItemClickListener {
    fun onItemClick(note: Note)
}