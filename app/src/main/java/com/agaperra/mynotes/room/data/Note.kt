package com.agaperra.mynotes.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    @PrimaryKey
    val date: String,
    val note: String?
)
