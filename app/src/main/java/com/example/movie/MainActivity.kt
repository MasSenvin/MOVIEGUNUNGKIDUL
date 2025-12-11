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

        val rvMovies = findViewById<RecyclerView>(R.id.rv_movies)
        rvMovies.layoutManager = GridLayoutManager(this, 2)

        val movieList = listOf(
            Movie(
                "Avengers: Endgame",
                R.drawable.m1,
                "Action, Adventure",
                "3 jam 1 menit",
                "Anthony & Joe Russo",
                "13+",
                "Avengers yang tersisa berusaha membalikkan dampak jentikan Thanos..."
            ),
            Movie(
                "Spider-Man: No Way Home",
                R.drawable.m2,
                "Action",
                "2 jam 28 menit",
                "Jon Watts",
                "13+",
                "Identitas Peter terbongkar, membuka kekacauan multiverse..."
            ),
            Movie(
                "Frozen II",
                R.drawable.m3,
                "Fantasy",
                "1 jam 43 menit",
                "Jennifer Lee, Chris Buck",
                "SU",
                "Elsa mendengar suara misterius yang memanggilnya dari hutan ajaib..."
            ),
            Movie(
                "The Lion King",
                R.drawable.m4,
                "Animation, Adventure, Drama, Family",
                "1 jam 58 menit",
                "Jon Favreau",
                "SU",
                "Simba harus menghadapi masa lalunya dan kembali merebut Pride Rock..."
            ),
            Movie(
                "Joker",
                R.drawable.m5,
                "Crime, Drama, Thriller",
                "2 jam 2 menit",
                "Todd Phillips",
                "17+",
                "Arthur Fleck jatuh ke jurang kegilaan hingga melahirkan Joker..."
            ),
            Movie(
                "Toy Story 4",
                R.drawable.m6,
                "Animation, Comedy, Family",
                "1 jam 40 menit",
                "Josh Cooley",
                "SU",
                "Woody bertemu Bo Peep dan menemukan arti baru menjadi mainan..."
            )
        )

        rvMovies.adapter = MovieAdapter(movieList)

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
