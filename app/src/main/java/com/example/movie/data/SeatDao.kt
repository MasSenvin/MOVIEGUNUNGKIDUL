package com.example.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SeatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(seats: List<Seat>)

    @Query("SELECT * FROM seats WHERE showTimeId = :showTimeId AND booked = 0")
    fun getAvailableSeats(showTimeId: Int): List<Seat>

    @Query("SELECT * FROM seats WHERE showTimeId = :showTimeId ORDER BY seatLabel ASC")
    fun getByShowTime(showTimeId: Int): List<Seat>

    @Query("UPDATE seats SET booked = 1 WHERE showTimeId = :showTimeId AND seatLabel = :seatLabel")
    fun bookSeat(showTimeId: Int, seatLabel: String): Int
}
