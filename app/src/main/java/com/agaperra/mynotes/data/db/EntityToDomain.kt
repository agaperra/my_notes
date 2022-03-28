package com.agaperra.mynotes.data.db

import android.annotation.SuppressLint
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.domain.model.NoteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@JvmName("toDomainNoteEntityList")
fun Flow<List<Note>>.toDomain(): Flow<List<NoteItem>> = map { list ->
    list.map { entity ->
        NoteItem(
            position = entity.position,
            title = entity.title,
            edit_date = entity.edit_date,
            create_date = entity.create_date,
            note = entity.note
        )
    }
}

@JvmName("toDomainNoteEntity")
fun Flow<Note>.toDomain(): Flow<NoteItem> = map { entity ->
    NoteItem(
        position = entity.position,
        title = entity.title,
        edit_date = entity.edit_date,
        create_date = entity.create_date,
        note = entity.note
    )
}
