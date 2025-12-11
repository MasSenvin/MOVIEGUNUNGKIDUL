package com.example.movie

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.data.AppDatabase
import com.example.movie.data.Seat

class SeatSelectionActivity : AppCompatActivity() {
    private var selectedSeat: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        val showTimeId = intent.getIntExtra("SHOWTIME_ID", -1)
        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "-"
        val date = intent.getStringExtra("DATE") ?: "-"
        val time = intent.getStringExtra("TIME") ?: "-"

        val tvInfo = findViewById<TextView>(R.id.tv_info)
        val grid = findViewById<GridLayout>(R.id.grid_seats)
        val btnLanjut = findViewById<Button>(R.id.btn_lanjut_seat)

        tvInfo.text = "$movieTitle\n$date $time"

        val db = AppDatabase.getDatabase(this)
        val seats = db.seatDao().getByShowTime(showTimeId)
        if (seats.isEmpty()) {
            Toast.makeText(this, "Kursi habis", Toast.LENGTH_SHORT).show()
        } else {
            renderSeats(grid, seats)
        }

        btnLanjut.setOnClickListener {
            val userId = SessionManager.getUserId(this)
            if (userId == null) {
                Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val seat = selectedSeat
            if (seat == null) {
                Toast.makeText(this, "Pilih kursi dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            PaymentActivity.start(this, showTimeId, seat, movieTitle, date, time)
        }
    }

    private fun renderSeats(grid: GridLayout, seats: List<Seat>) {
        var lastSelected: Button? = null
        seats.forEach { seat ->
            val b = Button(this)
            b.text = seat.seatLabel
            if (seat.booked) {
                b.isEnabled = false
                b.setBackgroundResource(R.drawable.bg_seat_booked)
            } else {
                b.setBackgroundResource(R.drawable.bg_seat_normal)
                b.setOnClickListener {
                    lastSelected?.setBackgroundResource(R.drawable.bg_seat_normal)
                    b.setBackgroundResource(R.drawable.bg_seat_selected)
                    lastSelected = b
                    selectedSeat = seat.seatLabel
                }
            }
            grid.addView(b)
        }
    }
}
