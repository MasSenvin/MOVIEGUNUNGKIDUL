package com.example.movie.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tickets",
    indices = [Index("userId"), Index("showTimeId")],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ShowTime::class,
            parentColumns = ["id"],
            childColumns = ["showTimeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val showTimeId: Int,
    val seatLabel: String,
    val purchaseDate: Long
)
