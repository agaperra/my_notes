package com.agaperra.mynotes.listener

import com.agaperra.mynotes.response.NoteResponse

interface OnItemClickListener {
    fun onItemClick(note: NoteResponse)
}