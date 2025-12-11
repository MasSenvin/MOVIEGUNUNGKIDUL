package com.example.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster: ImageView = view.findViewById(R.id.img_poster)
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        // tampil
        holder.tvJudul.text = movie.title
        holder.imgPoster.setImageResource(movie.imageResId)

        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, DetailActivity::class.java)

            intent.putExtra("JUDUL_FILM", movie.title)
            intent.putExtra("POSTER", movie.imageResId)
            intent.putExtra("GENRE", movie.genre)
            intent.putExtra("DURASI", movie.duration)
            intent.putExtra("SUTRADARA", movie.director)
            intent.putExtra("RATING", movie.rating)
            intent.putExtra("SINOPSIS", movie.synopsis)

            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movieList.size
}
