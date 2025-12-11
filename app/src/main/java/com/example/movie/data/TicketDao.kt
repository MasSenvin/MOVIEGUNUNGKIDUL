package com.example.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(ticket: Ticket): Long

    @Query("SELECT * FROM tickets WHERE userId = :userId ORDER BY purchaseDate DESC")
    fun getByUser(userId: Int): List<Ticket>
}
