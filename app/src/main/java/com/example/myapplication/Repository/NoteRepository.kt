package com.example.myapplication.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myapplication.Database.NoteDatabase
import com.example.myapplication.Model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NoteRepository {

    //repository to handle the business logic of commands
    companion object {
        private var noteDatabase: NoteDatabase? = null

        private fun initialiseDB(context: Context): NoteDatabase? {
            return NoteDatabase.getInstance(context)
        }

        fun insert(context: Context, note: Note) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.insert(note)
            }
        }

        fun getCardData(context: Context): LiveData<List<Note>>? {
            noteDatabase = initialiseDB(context)
            return noteDatabase?.getDao()?.getCardsData()
        }

        fun update(context: Context, note: Note) {
            noteDatabase = initialiseDB(context)

            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.update(note)
            }
        }

        fun delete(context: Context, note: Note) {
            noteDatabase = initialiseDB(context)
            CoroutineScope(IO).launch {
                noteDatabase?.getDao()?.delete(note)
            }
        }

    }


}