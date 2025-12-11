package com.example.movie

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

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
                val note = etDetail.text.toString()
                val msg = "Film: $judul\nTanggal: $selectedDate\nJam: $selectedTime\nCatatan: $note"
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun setupDateSelection() {
        val dateButtons = listOf(
            R.id.btn_tgl1, R.id.btn_tgl2, R.id.btn_tgl3, R.id.btn_tgl4, R.id.btn_tgl5,
            R.id.btn_tgl6, R.id.btn_tgl7, R.id.btn_tgl8, R.id.btn_tgl9, R.id.btn_tgl10
        )

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
