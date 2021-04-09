package com.agaperra.mynotes.listener

import com.agaperra.mynotes.data.Note

interface OnItemClickListener {
    fun onItemClick(note: Note)
}