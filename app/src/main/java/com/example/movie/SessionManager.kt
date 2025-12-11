package com.example.movie

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "movie_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_EMAIL = "email"

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setUser(ctx: Context, userId: Int, email: String) {
        prefs(ctx).edit().putInt(KEY_USER_ID, userId).putString(KEY_EMAIL, email).apply()
    }

    fun getUserId(ctx: Context): Int? {
        val id = prefs(ctx).getInt(KEY_USER_ID, -1)
        return if (id == -1) null else id
    }

    fun getEmail(ctx: Context): String? = prefs(ctx).getString(KEY_EMAIL, null)

    fun clear(ctx: Context) {
        prefs(ctx).edit().clear().apply()
    }
}
