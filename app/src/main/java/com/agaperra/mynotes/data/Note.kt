package com.agaperra.mynotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
        var title: String,
        @PrimaryKey
        var create_date: String,
        var edit_date: String,
        var note: String?
)