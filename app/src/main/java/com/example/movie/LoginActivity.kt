package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan layout Login (yang seharusnya sudah Anda miliki)
        setContentView(R.layout.activity_login)

        // Contoh: Link ke Register Activity dari halaman Login
        val tvRegisterLink = findViewById<TextView>(R.id.tv_register_link)
        tvRegisterLink?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // ... Logika Login lainnya
    }
}