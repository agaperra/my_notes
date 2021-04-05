package com.agaperra.mynotes.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.mynotes.App
import com.agaperra.mynotes.R
import com.agaperra.mynotes.helper.ItemTouchHelperAdapter
import com.agaperra.mynotes.helper.ItemTouchHelperViewHolder
import com.agaperra.mynotes.listener.OnItemClickListener
import com.agaperra.mynotes.repository.NotesRepository
import com.agaperra.mynotes.repository.NotesRepositoryImpl
import com.agaperra.mynotes.response.NoteResponse
import java.util.*
import kotlin.collections.ArrayList

class NoteAdapter(var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), ItemTouchHelperAdapter {

    private var notes = arrayListOf<NoteResponse>()
    private val notesRepository: NotesRepository =
        NotesRepositoryImpl(App.getNoteDao())

    fun setData(data: ArrayList<NoteResponse>) {
        this.notes = data
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ItemTouchHelperViewHolder {

        fun bind(note: NoteResponse) {
            itemView.findViewById<TextView>(R.id.header).text = note.title
            itemView.findViewById<TextView>(R.id.date).text = note.date
            itemView.findViewById<TextView>(R.id.text).text = note.note

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size


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
        val temp =notes[position].date
        notes.removeAt(position)
        notifyItemRemoved(position)
        dropNote(temp)
    }

    private fun dropNote(date: String) {
        notesRepository.dropNote(date)
    }

}