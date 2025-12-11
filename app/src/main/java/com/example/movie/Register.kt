package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.data.AppDatabase
import com.example.movie.data.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etNama: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var tvLoginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Menggunakan layout Register

        // Inisialisasi komponen
        etEmail = findViewById(R.id.et_email)
        etNama = findViewById(R.id.et_nama)
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
        val nama = etNama.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || nama.isEmpty()) {
            Toast.makeText(this, "Mohon isi semua kolom!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Kata sandi tidak cocok!", Toast.LENGTH_SHORT).show()
            etConfirmPassword.error = "Kata sandi tidak sama"
            return
        }

        val db = AppDatabase.getDatabase(this)
        val existing = db.userDao().findByEmail(email)
        if (existing != null) {
            Toast.makeText(this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(email = email, password = password, nama = nama)
        db.userDao().insertUser(user)
        Toast.makeText(this, "Pendaftaran berhasil untuk: $email", Toast.LENGTH_LONG).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
