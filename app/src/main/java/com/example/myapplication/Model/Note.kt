package com.example.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "note")
data class Note(
    var title: String,
    var description: String,
    var priority: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}