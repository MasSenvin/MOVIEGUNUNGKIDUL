package com.example.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShowTimeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(showTime: ShowTime): Long

    @Update
    fun update(showTime: ShowTime)

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    fun getByMovie(movieId: Int): List<ShowTime>

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId AND date = :date AND time = :time LIMIT 1")
    fun findByMovieAndSlot(movieId: Int, date: String, time: String): ShowTime?

    @Query("SELECT * FROM showtimes WHERE id = :id LIMIT 1")
    fun getById(id: Int): ShowTime?
}
