package com.example.myapplication.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.Model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note ORDER BY CASE WHEN priority = 'High' THEN 1 WHEN priority = 'Medium' THEN 2 WHEN priority = 'Low' THEN 3 ELSE 4 END, id DESC")
    fun getCardsData(): LiveData<List<Note>>

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)


}