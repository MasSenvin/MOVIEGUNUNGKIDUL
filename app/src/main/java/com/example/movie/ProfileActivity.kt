package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.data.AppDatabase

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userId = SessionManager.getUserId(this)
        if (userId == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val tvEmail = findViewById<TextView>(R.id.tv_email)
        val tvName = findViewById<TextView>(R.id.tv_name)
        val rv = findViewById<RecyclerView>(R.id.rv_tickets)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        tvEmail.text = SessionManager.getEmail(this) ?: "-"
        val user = AppDatabase.getDatabase(this).userDao().getById(userId)
        tvName.text = user?.nama ?: "Pengguna"

        val db = AppDatabase.getDatabase(this)
        val tickets = db.ticketDao().getByUser(userId)
        val displays = tickets.mapNotNull { t ->
            val show = db.showTimeDao().getById(t.showTimeId)
            val movie = show?.let { db.movieDao().findById(it.movieId) }
            if (show != null && movie != null) {
                TicketDisplay(
                    title = movie.title,
                    posterResId = movie.posterResId,
                    date = show.date,
                    time = show.time,
                    seat = t.seatLabel
                )
            } else null
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ProfileTicketAdapter(displays)

        btnLogout.setOnClickListener {
            SessionManager.clear(this)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
