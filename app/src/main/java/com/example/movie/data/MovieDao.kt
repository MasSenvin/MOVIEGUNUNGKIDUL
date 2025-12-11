package com.example.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<MovieEntity>)

    @Query("SELECT COUNT(*) FROM movies")
    fun count(): Int

    @Query("SELECT * FROM movies WHERE title = :title LIMIT 1")
    fun findByTitle(title: String): MovieEntity?

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    fun findById(id: Int): MovieEntity?
}
