package com.example.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val posterResId: Int,
    val genre: String,
    val duration: String,
    val director: String,
    val rating: String,
    val synopsis: String
)
