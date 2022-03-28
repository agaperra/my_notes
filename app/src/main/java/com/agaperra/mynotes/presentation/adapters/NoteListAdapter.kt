package com.agaperra.mynotes.presentation.adapters

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.agaperra.mynotes.R
import com.agaperra.mynotes.data.db.entity.Note
import com.agaperra.mynotes.data.db.NoteDatabase
import com.agaperra.mynotes.data.repository.NoteRepositoryImpl
import com.agaperra.mynotes.databinding.ItemNoteBinding
import com.agaperra.mynotes.domain.model.NoteItem
import com.agaperra.mynotes.domain.use_case.NoteCases
import com.agaperra.mynotes.presentation.adapters.diffutil.NotesDiffUtil
import com.agaperra.mynotes.util.helper.ItemTouchHelperAdapter
import com.agaperra.mynotes.util.helper.ItemTouchHelperViewHolder
import com.agaperra.mynotes.presentation.adapters.listeners.OnItemClickListener
import com.agaperra.mynotes.presentation.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NoteListAdapter (
    var onItemClickListener: OnItemClickListener,
    private val viewModel: MainViewModel,
    val context: Context
) :
    ListAdapter<NoteItem, NoteListAdapter.NoteListViewHolder>(
        NotesDiffUtil()
    ), ItemTouchHelperAdapter {

    @Inject
    lateinit var repositoryImpl: NoteRepositoryImpl

    @Inject
    lateinit var noteCases: NoteCases

    var notes = arrayListOf<NoteItem>()

    inner class NoteListViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPosition: Int) {

            val note = getItem(itemPosition)
            binding.header.text = note.title
            binding.date.text = note.create_date.take(10)
            binding.text.text = note.note


            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteListViewHolder(
            ItemNoteBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_note,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) =
        holder.bind(itemPosition = position)


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(notes, i, i + 1)
                // updatePosition(i, i+1, viewModel)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(notes, i, i - 1)
                //updatePosition(i, i-1, viewModel)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemDismiss(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.delete_note))
            .setPositiveButton(
                context.resources.getString(R.string.yes)
            ) { _: DialogInterface?, _: Int ->
                val temp = notes[position].create_date
                notes.removeAt(position)
                notifyItemRemoved(position)
                //dropNote(temp)
            }
            .setNegativeButton(
                context.resources.getString(R.string.chancel)
            ) { dialog: DialogInterface, _: Int ->
                notifyDataSetChanged()
                dialog.cancel()
            }
            .show()


    }

    private suspend fun dropNote(date: String, viewModel: MainViewModel) {
        noteCases.dropNote(date)
    }

    private fun getCount(): Int {
        var temp = 0
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            temp = repositoryImpl.getCount()
        }
        return temp
    }


    private fun updateNote(
        title: String?,
        create_date: String,
        edit_date: String,
        note: String?,
        viewModel: MainViewModel
    ) {
//        viewModel.viewModelScope.launch(Dispatchers.IO) {
//            repositoryImpl.updateNote(title, create_date, edit_date, note)
//        }
    }


}



