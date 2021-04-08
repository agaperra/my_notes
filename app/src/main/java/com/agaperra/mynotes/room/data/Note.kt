package com.agaperra.mynotes.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
        val title: String,
        @PrimaryKey
        val create_date: String,
        val edit_date: String,
        val note: String?
)
