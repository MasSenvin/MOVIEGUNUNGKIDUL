package com.example.movie.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "seats",
    indices = [Index("showTimeId"), Index(value = ["showTimeId", "seatLabel"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = ShowTime::class,
            parentColumns = ["id"],
            childColumns = ["showTimeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Seat(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val showTimeId: Int,
    val seatLabel: String,
    val booked: Boolean = false
)
