package com.example.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.data.AppDatabase

class LoginActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnMasuk = findViewById<Button>(R.id.btn_login)
        val etEmail = findViewById<EditText>(R.id.et_login_email)
        val etPassword = findViewById<EditText>(R.id.et_login_password)

        btnMasuk.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi email dan password dulu", Toast.LENGTH_SHORT).show()
            } else {
                val db = AppDatabase.getDatabase(this)
                val user = db.userDao().cekLogin(email, pass)
                if (user != null) {
                    SessionManager.setUser(this, user.id, user.email)
                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()

                    val redirect = intent.getStringExtra("REDIRECT")
                    if (redirect == "seat") {
                        val title = intent.getStringExtra("MOVIE_TITLE") ?: return@setOnClickListener
                        val date = intent.getStringExtra("DATE") ?: return@setOnClickListener
                        val time = intent.getStringExtra("TIME") ?: return@setOnClickListener

                        val movie = db.movieDao().findByTitle(title)
                        if (movie != null) {
                            var show = db.showTimeDao().findByMovieAndSlot(movie.id, date, time)
                            if (show == null) {
                                val quota = 30
                                val newId = db.showTimeDao().insert(com.example.movie.data.ShowTime(movieId = movie.id, date = date, time = time, quota = quota, available = quota)).toInt()
                                val seats = (1..quota).map { com.example.movie.data.Seat(showTimeId = newId, seatLabel = "S$it") }
                                db.seatDao().insertAll(seats)
                                show = db.showTimeDao().findByMovieAndSlot(movie.id, date, time)
                            }
                            if (show != null) {
                                val i = Intent(this, SeatSelectionActivity::class.java)
                                i.putExtra("SHOWTIME_ID", show.id)
                                i.putExtra("MOVIE_TITLE", movie.title)
                                i.putExtra("DATE", date)
                                i.putExtra("TIME", time)
                                startActivity(i)
                            } else {
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    } else if (redirect == "profile") {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    finish()
                } else {
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val tvRegisterLink = findViewById<TextView>(R.id.tv_register_link)
        tvRegisterLink?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
