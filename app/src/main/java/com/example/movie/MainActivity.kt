package com.example.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.movie.data.AppDatabase
import com.example.movie.data.MovieEntity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvMovies = findViewById<RecyclerView>(R.id.rv_movies)
        rvMovies.layoutManager = GridLayoutManager(this, 2)

        val db = AppDatabase.getDatabase(this)

        if (db.movieDao().count() == 0) {
            val initialMovies = listOf(
                MovieEntity(title = "Avengers: Endgame", posterResId = R.drawable.m1, genre = "Action, Adventure", duration = "3 jam 1 menit", director = "Anthony & Joe Russo", rating = "13+", synopsis = "Avengers yang tersisa berusaha membalikkan dampak jentikan Thanos..."),
                MovieEntity(title = "Spider-Man: No Way Home", posterResId = R.drawable.m2, genre = "Action", duration = "2 jam 28 menit", director = "Jon Watts", rating = "13+", synopsis = "Identitas Peter terbongkar, membuka kekacauan multiverse..."),
                MovieEntity(title = "Frozen II", posterResId = R.drawable.m3, genre = "Fantasy", duration = "1 jam 43 menit", director = "Jennifer Lee, Chris Buck", rating = "SU", synopsis = "Elsa mendengar suara misterius yang memanggilnya dari hutan ajaib..."),
                MovieEntity(title = "The Lion King", posterResId = R.drawable.m4, genre = "Animation, Adventure, Drama, Family", duration = "1 jam 58 menit", director = "Jon Favreau", rating = "SU", synopsis = "Simba harus menghadapi masa lalunya dan kembali merebut Pride Rock..."),
                MovieEntity(title = "Joker", posterResId = R.drawable.m5, genre = "Crime, Drama, Thriller", duration = "2 jam 2 menit", director = "Todd Phillips", rating = "17+", synopsis = "Arthur Fleck jatuh ke jurang kegilaan hingga melahirkan Joker..."),
                MovieEntity(title = "Toy Story 4", posterResId = R.drawable.m6, genre = "Animation, Comedy, Family", duration = "1 jam 40 menit", director = "Josh Cooley", rating = "SU", synopsis = "Woody bertemu Bo Peep dan menemukan arti baru menjadi mainan...")
            )
            db.movieDao().insertAll(initialMovies)
        }

        val movieEntities = db.movieDao().getAll()
        val movieList = movieEntities.map {
            Movie(
                title = it.title,
                imageResId = it.posterResId,
                genre = it.genre,
                duration = it.duration,
                director = it.director,
                rating = it.rating,
                synopsis = it.synopsis
            )
        }

        rvMovies.adapter = MovieAdapter(movieList)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_account -> {
                    val userId = SessionManager.getUserId(this)
                    if (userId == null) {
                        val i = Intent(this, LoginActivity::class.java)
                        i.putExtra("REDIRECT", "profile")
                        startActivity(i)
                    } else {
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                    true
                }
                else -> false
            }
        }
    }
}
