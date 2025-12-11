package com.example.movie.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "showtimes",
    indices = [Index("movieId"), Index(value = ["movieId", "date", "time"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShowTime(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val movieId: Int,
    val date: String,
    val time: String,
    val quota: Int,
    val available: Int
)
