package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.data.AppDatabase
import com.example.movie.data.ShowTime
import com.example.movie.data.Seat
import com.example.movie.data.Ticket

class MovieScheduleActivity : AppCompatActivity() {

    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_schedule)

        val judul = intent.getStringExtra("JUDUL_FILM")
        val poster = intent.getIntExtra("POSTER", 0)

        val tvJudul = findViewById<TextView>(R.id.tv_judul_schedule)
        val imgPoster = findViewById<ImageView>(R.id.img_poster_schedule)
        val btnLanjut = findViewById<Button>(R.id.btn_lanjut)
        val btnBack = findViewById<ImageView>(R.id.btn_back_schedule)
        val etDetail = findViewById<EditText>(R.id.et_detail)

        tvJudul.text = judul
        imgPoster.setImageResource(poster)

        setupDateSelection()
        setupTimeSelection()

        btnLanjut.setOnClickListener {
            if (selectedDate == null || selectedTime == null) {
                Toast.makeText(this, "Pilih tanggal dan jam dulu ya", Toast.LENGTH_SHORT).show()
            } else {
                val userId = SessionManager.getUserId(this)
                if (userId == null) {
                    Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, LoginActivity::class.java)
                    i.putExtra("REDIRECT", "seat")
                    i.putExtra("MOVIE_TITLE", judul)
                    i.putExtra("DATE", selectedDate!!)
                    i.putExtra("TIME", selectedTime!!)
                    startActivity(i)
                    return@setOnClickListener
                }

                val db = AppDatabase.getDatabase(this)
                val movie = judul?.let { db.movieDao().findByTitle(it) }
                if (movie == null) {
                    Toast.makeText(this, "Film tidak ditemukan di database", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val date = selectedDate!!
                val time = selectedTime!!
                var show = db.showTimeDao().findByMovieAndSlot(movie.id, date, time)
                if (show == null) {
                    val quota = 30
                    val newId = db.showTimeDao().insert(ShowTime(movieId = movie.id, date = date, time = time, quota = quota, available = quota)).toInt()
                    show = db.showTimeDao().findByMovieAndSlot(movie.id, date, time)
                    if (show == null) {
                        Toast.makeText(this, "Gagal membuat jadwal", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val seats = mutableListOf<Seat>()
                    val rows = listOf('A','B','C','D','E')
                    for (r in rows) {
                        for (c in 1..6) {
                            seats.add(Seat(showTimeId = newId, seatLabel = "$r$c"))
                        }
                    }
                    db.seatDao().insertAll(seats)
                }

                val intent = Intent(this, SeatSelectionActivity::class.java)
                intent.putExtra("SHOWTIME_ID", show.id)
                intent.putExtra("MOVIE_TITLE", movie.title)
                intent.putExtra("DATE", date)
                intent.putExtra("TIME", time)
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun setupDateSelection() {
        val dateButtons = listOf(
            R.id.btn_tgl1, R.id.btn_tgl2, R.id.btn_tgl3, R.id.btn_tgl4, R.id.btn_tgl5,
            R.id.btn_tgl6, R.id.btn_tgl7, R.id.btn_tgl8, R.id.btn_tgl9, R.id.btn_tgl10
        )

        resetDateButtons(dateButtons)

        dateButtons.forEach { id ->
            val btn = findViewById<Button>(id)
            btn.setOnClickListener {
                selectedDate = btn.text.toString()
                resetDateButtons(dateButtons)
                btn.isSelected = true
                btn.setBackgroundResource(R.drawable.bg_date_selected)
            }
        }
    }

    private fun setupTimeSelection() {
        val timeButtons = listOf(
            R.id.btn_jam1, R.id.btn_jam2, R.id.btn_jam3, R.id.btn_jam4
        )

        resetTimeButtons(timeButtons)

        timeButtons.forEach { id ->
            val btn = findViewById<Button>(id)
            btn.setOnClickListener {
                selectedTime = btn.text.toString()
                resetTimeButtons(timeButtons)
                btn.isSelected = true
                btn.setBackgroundResource(R.drawable.bg_time_selected)
            }
        }
    }

    private fun resetDateButtons(list: List<Int>) {
        list.forEach { id ->
            val btn = findViewById<Button>(id)
            btn.isSelected = false
            btn.setBackgroundResource(R.drawable.bg_date_normal)
        }
    }

    private fun resetTimeButtons(list: List<Int>) {
        list.forEach { id ->
            val btn = findViewById<Button>(id)
            btn.isSelected = false
            btn.setBackgroundResource(R.drawable.bg_time_normal)
        }
    }
}
