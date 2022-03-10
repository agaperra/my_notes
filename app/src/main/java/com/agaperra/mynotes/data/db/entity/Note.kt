package com.agaperra.mynotes.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
        var position: Int,
        var title: String?,
        @PrimaryKey
        var edit_date: String,
        var create_date: String,
        var note: String?
)