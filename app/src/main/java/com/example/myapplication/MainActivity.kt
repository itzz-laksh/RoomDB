package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Note
import com.example.myapplication.ViewModel.NoteViewModel
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var getNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        //initialize the viewModel
        noteViewModel = NoteViewModel()

        setBackgroundView()


        //set Adapter For Priority Spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority_levels,
            R.layout.custom_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter

        // Check if the activity was opened for editing an existing note
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            getNote = bundle.getSerializable("note") as Note

            val title = getNote.title
            val description = getNote.description
            val priority = getNote.priority

            binding.editTextTitle.setText(title)
            binding.editTextDescription.setText(description)

            val priorityIndex = adapter.getPosition(priority)
            binding.spinnerPriority.setSelection(priorityIndex)

            // Change the button text and behavior for updating instead of saving
            binding.buttonSave.text = "Update"
            binding.buttonSave.setOnClickListener {
                if (validateInput()) {
                    updateData()
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
            // Change the button text and behavior for deleting instead of fetching
            binding.buttonFetch.text = "Delete"
            binding.buttonFetch.setOnClickListener {
                noteViewModel.delete(applicationContext, getNote)
                onBackPressed()
                finish()
            }
        } else {
            //other wise new item
            binding.buttonFetch.visibility = View.VISIBLE
            binding.buttonSave.setOnClickListener {
                if (validateInput()) {
                    saveData()
                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }

            binding.buttonFetch.setOnClickListener {
                val intent = Intent(this, AllNotesActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun setBackgroundView() {
        //setView
        Glide.with(this)
            .load(R.drawable.background)
            .into(binding.imageView);
    }

    // validate the checks for user to fill all the details
    private fun validateInput(): Boolean {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val priority = binding.spinnerPriority.selectedItem.toString()

        return title.isNotEmpty() && description.isNotEmpty() && priority.isNotEmpty()
    }

    // save data function
    private fun saveData() {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val priority = binding.spinnerPriority.selectedItem.toString()

        // logic to save the data (e.g., to a database or perform some action)
        noteViewModel.insert(applicationContext, Note(title, description, priority))
        // Display a toast message indicating the data is saved
        Toast.makeText(
            this,
            "Data saved Successfully",
            Toast.LENGTH_SHORT
        ).show()

        //clear the fields
        binding.editTextTitle.text.clear()
        binding.editTextDescription.text.clear()

        //keyboard hide
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTitle.windowToken, 0)
    }

    // update data function
    private fun updateData() {
        getNote.title = binding.editTextTitle.text.toString()
        getNote.description = binding.editTextDescription.text.toString()
        getNote.priority = binding.spinnerPriority.selectedItem.toString()

        // logic to update the data (e.g., to a database or perform some action)
        noteViewModel.update(applicationContext, getNote)
        // Display a toast message indicating the data is updated
        Toast.makeText(
            this,
            "Data updated Successfully",
            Toast.LENGTH_SHORT
        ).show()
        onBackPressed()
        finish()
    }


}

