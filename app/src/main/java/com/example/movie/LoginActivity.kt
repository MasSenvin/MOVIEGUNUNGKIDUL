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

    // 1. Siapkan variabel untuk Database
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 2. Inisialisasi Database
        db = AppDatabase.getDatabase(this)

        // 3. Hubungkan variabel dengan komponen di Layout
        val buttonKembali = findViewById<ImageButton>(R.id.button_kembali)
        val buttonMasuk = findViewById<Button>(R.id.button_masuk)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editPassword = findViewById<EditText>(R.id.edit_password)
        val textDaftar = findViewById<TextView>(R.id.text_daftar)

        // --- LOGIKA TOMBOL KEMBALI ---
        buttonKembali.setOnClickListener {
            finish()
        }

        // --- LOGIKA DAFTAR (Klik teks "Daftar Sekarang") ---
        // Karena kita belum punya halaman register khusus, kita pakai cara cepat ini.
        // Isi email & password -> Klik teks "Daftar Sekarang" -> Data tersimpan.
        textDaftar.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val userBaru = User(email = email, password = password, nama = "User Baru")
                try {
                    db.userDao().insertUser(userBaru)
                    Toast.makeText(this, "Berhasil Daftar! Sekarang silakan klik MASUK", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Gagal Daftar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Isi Email & Sandi dulu untuk mendaftar", Toast.LENGTH_SHORT).show()
            }
        }

        // --- LOGIKA LOGIN (Klik Tombol MASUK) ---
        buttonMasuk.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon isi email dan kata sandi", Toast.LENGTH_SHORT).show()
            } else {
                // Cek ke Database: Apakah ada user ini?
                val user = db.userDao().cekLogin(email, password)

                if (user != null) {
                    // KETEMU! Login sukses
                    Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                    // Pindah ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Tutup layar login
                } else {
                    // TIDAK KETEMU
                    Toast.makeText(this, "Email atau Password Salah!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}