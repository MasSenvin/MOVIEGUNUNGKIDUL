package com.example.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class TicketDisplay(
    val title: String,
    val posterResId: Int,
    val date: String,
    val time: String,
    val seat: String
)

class ProfileTicketAdapter(private val items: List<TicketDisplay>) :
    RecyclerView.Adapter<ProfileTicketAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val imgPoster: ImageView = view.findViewById(R.id.img_ticket_poster)
        val tvTitle: TextView = view.findViewById(R.id.tv_ticket_title)
        val tvInfo: TextView = view.findViewById(R.id.tv_ticket_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.imgPoster.setImageResource(it.posterResId)
        holder.tvTitle.text = it.title
        holder.tvInfo.text = "${it.date} • ${it.time} • Kursi ${it.seat}"
    }

    override fun getItemCount(): Int = items.size
}
