package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ===== Dari Branch "panii" =====
        val btnBack = findViewById<ImageButton>(R.id.button_kembali)
        val btnMasuk = findViewById<Button>(R.id.button_masuk)
        val etEmail = findViewById<EditText>(R.id.edit_email)
        val etPassword = findViewById<EditText>(R.id.edit_password)

        btnBack.setOnClickListener { finish() }

        btnMasuk.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi email dan password dulu", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login dummy: $email", Toast.LENGTH_SHORT).show()
            }
        }

        // ===== Dari Branch "main" =====
        val tvRegisterLink = findViewById<TextView>(R.id.tv_register_link)
        tvRegisterLink?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
