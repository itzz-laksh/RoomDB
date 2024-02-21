package com.example.myapplication.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.Note
import com.example.myapplication.Repository.NoteRepository

class NoteViewModel : ViewModel() {

    fun insert(context: Context, note: Note) {
        NoteRepository.insert(context, note)
    }

    fun getCardsData(context: Context): LiveData<List<Note>>? {
        return NoteRepository.getCardData(context)
    }

    fun update(context: Context, note: Note) {
        NoteRepository.update(context, note)
    }

    fun delete(context: Context, note: Note) {
        NoteRepository.delete(context, note)
    }

}