package com.agaperra.mynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agaperra.mynotes.App
import com.agaperra.mynotes.R
import com.agaperra.mynotes.listener.OnItemClickListener
import com.agaperra.mynotes.repository.NotesRepository
import com.agaperra.mynotes.repository.NotesRepositoryImpl
import com.agaperra.mynotes.response.NoteResponse
import com.agaperra.mynotes.room.data.Note

class NoteAdapter(var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notes: List<NoteResponse> = arrayListOf()

    fun setData(data: List<NoteResponse>) {
        this.notes = data
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: NoteResponse) {
            itemView.findViewById<TextView>(R.id.header).text = note.title
            itemView.findViewById<TextView>(R.id.date).text = note.date
            itemView.findViewById<TextView>(R.id.text).text = note.note

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(note)
            }
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

}