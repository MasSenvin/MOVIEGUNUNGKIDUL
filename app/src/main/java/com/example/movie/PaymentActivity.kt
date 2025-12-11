package com.example.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.data.AppDatabase
import com.example.movie.data.Ticket

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val showTimeId = intent.getIntExtra("SHOWTIME_ID", -1)
        val seatLabel = intent.getStringExtra("SEAT_LABEL") ?: "-"
        val movieTitle = intent.getStringExtra("MOVIE_TITLE") ?: "-"
        val date = intent.getStringExtra("DATE") ?: "-"
        val time = intent.getStringExtra("TIME") ?: "-"

        val imgPoster = findViewById<ImageView>(R.id.img_poster_pay)
        val tvTitle = findViewById<TextView>(R.id.tv_title_pay)
        val tvDateTime = findViewById<TextView>(R.id.tv_datetime_pay)
        val tvSeat = findViewById<TextView>(R.id.tv_seat_pay)
        val tvPrice = findViewById<TextView>(R.id.tv_price)
        val tvTotal = findViewById<TextView>(R.id.tv_total)
        val btnBayar = findViewById<Button>(R.id.btn_bayar)

        val db = AppDatabase.getDatabase(this)
        val movie = db.movieDao().findByTitle(movieTitle)
        if (movie != null) {
            imgPoster.setImageResource(movie.posterResId)
        }
        tvTitle.text = movieTitle
        tvDateTime.text = "$date • $time"
        tvSeat.text = "Kursi: $seatLabel"
        val price = 35000
        tvPrice.text = "Rp %,d".format(price)
        tvTotal.text = "Rp %,d".format(price)

        btnBayar.setOnClickListener {
            val userId = SessionManager.getUserId(this)
            if (userId == null) {
                Toast.makeText(this, "Silakan login dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val db = AppDatabase.getDatabase(this)
            val booked = db.seatDao().bookSeat(showTimeId, seatLabel)
            if (booked == 0) {
                Toast.makeText(this, "Gagal memesan kursi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val show = db.showTimeDao().getById(showTimeId)
            if (show != null) db.showTimeDao().update(show.copy(available = show.available - 1))

            db.ticketDao().insert(
                Ticket(userId = userId, showTimeId = showTimeId, seatLabel = seatLabel, purchaseDate = System.currentTimeMillis())
            )

            Toast.makeText(this, "Pembayaran berhasil", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
            finish()
        }
    }

    companion object {
        fun start(ctx: Context, showTimeId: Int, seatLabel: String, title: String, date: String, time: String) {
            val i = Intent(ctx, PaymentActivity::class.java)
            i.putExtra("SHOWTIME_ID", showTimeId)
            i.putExtra("SEAT_LABEL", seatLabel)
            i.putExtra("MOVIE_TITLE", title)
            i.putExtra("DATE", date)
            i.putExtra("TIME", time)
            ctx.startActivity(i)
        }
    }
}
