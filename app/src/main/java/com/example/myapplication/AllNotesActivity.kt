package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.NoteAdapter
import com.example.myapplication.Model.Note
import com.example.myapplication.ViewModel.NoteViewModel
import com.example.myapplication.databinding.ActivityAllNotesBinding


class AllNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllNotesBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllNotesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //set adapter
        noteAdapter = NoteAdapter(mutableListOf<Note>() as ArrayList<Note>) { note ->
            openUpdateNoteActivity(note)
        }
        binding.recyclerViewNotes.adapter = noteAdapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        //initialize viewModel
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Observe the LiveData and update UI accordingly
        noteViewModel.getCardsData(applicationContext)?.observe(this, Observer { notes ->

            // show "No data found!!" text if  list is empty
            if (notes.isNotEmpty()) {
                binding.textViewNoItem.visibility = View.GONE
            } else {
                binding.textViewNoItem.visibility = View.VISIBLE
            }

            // update the adapter with new list
            noteAdapter.updateData(notes as ArrayList)
        })

    }

    //handling the callback click listener for item in adapter
    private fun openUpdateNoteActivity(note: Note) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("note", note)
            // Add other necessary extras as needed
        }
        startActivity(intent)
    }
}
