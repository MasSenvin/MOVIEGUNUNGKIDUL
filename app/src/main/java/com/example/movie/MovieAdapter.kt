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

        holder.tvJudul.text = movie.title
        holder.imgPoster.setImageResource(movie.imageResId)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)

            // --- KIRIM DATA KE DETAIL ---
            intent.putExtra("JUDUL_FILM", movie.title)
            intent.putExtra("YOUTUBE_ID", movie.youtubeId) // Kirim ID Youtube

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}