package com.example.myapplication.Adapter


import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Note
import com.example.myapplication.R


class NoteAdapter(private val notes: ArrayList<Note>, private val onItemClick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNoteItemTitle: TextView = itemView.findViewById(R.id.textViewNoteItemTitle)
        val textViewNoteItemDesc: TextView = itemView.findViewById(R.id.textViewNoteItemDesc)
        val textViewNoteItemPriority: TextView = itemView.findViewById(R.id.textViewNoteItemPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return NoteViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        //check for coloring the priority part
        //high -> red, medium -> blue, low -> orange
        val priorityHtml = when (currentNote.priority.toLowerCase()) {
            "high" -> "<font color='red'>${currentNote.priority}</font>"
            "medium" -> "<font color='blue'>${currentNote.priority}</font>"
            "low" -> "<font color='#FFA500'>${currentNote.priority}</font>"
            else -> currentNote.priority
        }

        // set texts dynamically
        holder.textViewNoteItemTitle.text = "Title: ${currentNote.title}"
        holder.textViewNoteItemDesc.text = "Description: ${currentNote.description}"
        holder.textViewNoteItemPriority.text =
            Html.fromHtml("Priority: $priorityHtml",
                Html.FROM_HTML_MODE_LEGACY
            )

        //item click listener
        holder.itemView.setOnClickListener {
            onItemClick(currentNote)
        }
    }

    override fun getItemCount() = notes.size

    // update the data as soon as action perform
    fun updateData(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }





}
