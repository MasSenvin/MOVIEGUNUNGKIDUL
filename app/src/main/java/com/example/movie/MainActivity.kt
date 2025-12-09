package com.example.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- SETUP RECYCLER VIEW ---
        val rvMovies = findViewById<RecyclerView>(R.id.rv_movies)
        rvMovies.layoutManager = GridLayoutManager(this, 2)

        // --- DATA FILM DAN ID YOUTUBE ---
        val movieList = listOf(
            // FILM 1: AVENGERS (Pake link yang kamu kasih tadi: TcMBFSGVi1c)
            Movie("Avengers: Endgame", R.drawable.m1, "TcMBFSGVi1c"),

            // FILM LAIN (Saya kasih contoh trailer lain biar beda-beda)
            Movie("Spider-Man: No Way Home", R.drawable.m2, "JfVOs4VSpmA"),
            Movie("Frozen II", R.drawable.m3, "Zi4LMpSDccc"),
            Movie("The Lion King", R.drawable.m4, "7TavVZMewpY"),
            Movie("Joker", R.drawable.m5, "zAGVQLHvwOY"),
            Movie("Toy Story 4", R.drawable.m6, "wmiIUN-7qhE")
        )

        val adapter = MovieAdapter(movieList)
        rvMovies.adapter = adapter

        // --- SETUP NAVIGASI BAWAH ---
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_account -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}