package com.agaperra.mynotes.adapter

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView

import com.agaperra.mynotes.R
import com.agaperra.mynotes.data.Note
import com.agaperra.mynotes.data.NoteDatabase
import com.agaperra.mynotes.data.NoteRepository
import com.agaperra.mynotes.helper.ItemTouchHelperAdapter
import com.agaperra.mynotes.helper.ItemTouchHelperViewHolder
import com.agaperra.mynotes.listener.OnItemClickListener
import com.agaperra.mynotes.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


open class NoteAdapter(var onItemClickListener: OnItemClickListener, application: Application, private val viewModel: MainViewModel, val context: Context) :
        RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), ItemTouchHelperAdapter {

    open var notes = arrayListOf<Note>()
    private lateinit var recycler: RecyclerView
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            ItemTouchHelperViewHolder {

        fun bind(note: Note) {
            itemView.findViewById<TextView>(R.id.header).text = note.title
            itemView.findViewById<TextView>(R.id.date).text = note.create_date.take(10)
            itemView.findViewById<TextView>(R.id.text).text = note.note


            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note)
            }
        }

        override fun onItemSelected() {
        }

        override fun onItemClear() {
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
//        updateNote(notes[position].title, notes[position].create_date, notes[position].edit_date, notes[position].note, position, viewModel)
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size


    fun setRecycler(recyclerView: RecyclerView){
        recycler=recyclerView
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(notes, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(notes, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.delete_note))
                .setPositiveButton(
                        context.resources.getString(R.string.yes)
                ) { _: DialogInterface?, _: Int ->
                    val temp =notes[position].create_date
                    notes.removeAt(position)
                    notifyItemRemoved(position)
                    dropNote(temp, viewModel =viewModel )
                }
                .setNegativeButton(
                        context.resources.getString(R.string.chancel)
                ) { dialog: DialogInterface, _: Int ->
                    notifyDataSetChanged()
                    dialog.cancel() }
                .show()


    }

    private fun dropNote(date: String, viewModel: MainViewModel) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            repository.dropNote(date)
        }
    }

    private fun updateNote(title: String, create_date: String, edit_date: String, note: String?,viewModel: MainViewModel){
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(Note(title, create_date, edit_date, note))
        }
    }

    fun addItems(note: List<Note>) = notes.addAll(note)

    fun clearItems() = notes.clear()


}

