package com.example.movie

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private var showingSynopsis = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Ambil data dari Intent
        val judul = intent.getStringExtra("JUDUL_FILM") ?: "-"
        val poster = intent.getIntExtra("POSTER", -1)
        val genre = intent.getStringExtra("GENRE") ?: "-"
        val durasi = intent.getStringExtra("DURASI") ?: "-"
        val sutradara = intent.getStringExtra("SUTRADARA") ?: "-"
        val rating = intent.getStringExtra("RATING") ?: "-"
        val sinopsis = intent.getStringExtra("SINOPSIS") ?: "-"

        // View
        val imgPoster = findViewById<ImageView>(R.id.img_poster_small)
        val tvJudul = findViewById<TextView>(R.id.tv_detail_judul)
        val tvGenre = findViewById<TextView>(R.id.tv_genre)
        val tvDurasi = findViewById<TextView>(R.id.tv_durasi)
        val tvSutradara = findViewById<TextView>(R.id.tv_sutradara)
        val tvRating = findViewById<TextView>(R.id.tv_rating)

        val layoutJadwal = findViewById<LinearLayout>(R.id.layout_jadwal)
        val tvSinopsis = findViewById<TextView>(R.id.tv_sinopsis)

        val btnToggle = findViewById<Button>(R.id.btn_toggle)
        val btnLanjut = findViewById<Button>(R.id.btn_lanjut)
        val btnBack = findViewById<Button>(R.id.btn_back)

        // Set Data
        if (poster != -1) imgPoster.setImageResource(poster)
        tvJudul.text = judul
        tvGenre.text = genre
        tvDurasi.text = durasi
        tvSutradara.text = sutradara
        tvRating.text = "Rating: $rating"
        tvSinopsis.text = sinopsis

        // Default: Schedule tampil, Sinopsis tersembunyi
        tvSinopsis.visibility = View.GONE
        layoutJadwal.visibility = View.VISIBLE

        // Tombol SINOPSIS - toggle
        btnToggle.setOnClickListener {
            if (!showingSynopsis) {
                // Tampilkan Sinopsis, sembunyikan jadwal
                layoutJadwal.visibility = View.GONE
                tvSinopsis.visibility = View.VISIBLE
                btnToggle.text = "Tutup Sinopsis"
                showingSynopsis = true
            } else {
                // Tampilkan jadwal, sembunyikan sinopsis
                layoutJadwal.visibility = View.VISIBLE
                tvSinopsis.visibility = View.GONE
                btnToggle.text = "Sinopsis"
                showingSynopsis = false
            }
        }

        btnBack.setOnClickListener { finish() }

        btnLanjut.setOnClickListener {
            Toast.makeText(this, "Lanjut ditekan!", Toast.LENGTH_SHORT).show()
        }
    }
}
