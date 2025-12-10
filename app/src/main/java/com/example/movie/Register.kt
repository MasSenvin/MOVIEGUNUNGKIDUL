package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Menggunakan layout Register

        // Inisialisasi komponen
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnRegister = findViewById(R.id.btn_register)
        tvLoginLink = findViewById(R.id.tv_login_link)

        // Aksi tombol DAFTAR
        btnRegister.setOnClickListener {
            handleRegister()
        }

        // Aksi link ke halaman Login
        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleRegister() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Mohon isi semua kolom!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Kata sandi tidak cocok!", Toast.LENGTH_SHORT).show()
            etConfirmPassword.error = "Kata sandi tidak sama"
            return
        }

        // --- Logika Pendaftaran ke Backend/Database di sini ---

        Toast.makeText(this, "Pendaftaran berhasil untuk: $email", Toast.LENGTH_LONG).show()

        // Contoh: Pindah ke Home/Main Activity
        // val intent = Intent(this, HomeActivity::class.java)
        // startActivity(intent)
        // finish()
    }
}