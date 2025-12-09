package com.example.movie

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val judul = intent.getStringExtra("JUDUL_FILM")
        val youtubeId = intent.getStringExtra("YOUTUBE_ID") // Tangkap ID

        val tvJudul = findViewById<TextView>(R.id.tv_detail_judul)
        val webView = findViewById<WebView>(R.id.webview_player)
        val btnBack = findViewById<Button>(R.id.btn_back_detail)

        tvJudul.text = judul

        // --- SETTING YOUTUBE ---
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

        if (youtubeId != null) {
            // Ini akan menggabungkan Link Embed + ID Video kamu
            val youtubeLink = "https://www.youtube.com/embed/$youtubeId"
            webView.loadUrl(youtubeLink)
        }
        // -----------------------

        btnBack.setOnClickListener {
            finish()
        }
    }
}