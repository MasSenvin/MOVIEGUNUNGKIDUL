package com.example.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun cekLogin(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun findByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getById(id: Int): User?
}
