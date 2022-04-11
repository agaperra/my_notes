package com.agaperra.mynotes.presentation.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.agaperra.mynotes.domain.model.NoteItem

class NotesDiffUtil : DiffUtil.ItemCallback<NoteItem>() {

    override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem) =
        oldItem.edit_date == newItem.edit_date

    override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem) =
        oldItem == newItem

}