package com.ast.app.network.utils

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistentCookieJar(private val prefs: SharedPreferences) : CookieJar {
    private var cookies: MutableList<Cookie> = loadCookies().toMutableList()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies.filter { it.matches(url) }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        this.cookies.removeAll { it.matches(url) }
        this.cookies.addAll(cookies)
        saveCookies()
    }

    private fun saveCookies() {
        val encoded = cookies.joinToString(";") { CookieEncoder.encode(it) }
        prefs.edit().putString("cookies", encoded).apply()
    }

    private fun loadCookies(): List<Cookie> {
        val cookieString = prefs.getString("cookies", "") ?: return emptyList()
        return cookieString.split(";").mapNotNull { CookieEncoder.decode(it) }
    }

    fun clear() {
        cookies.clear()
        prefs.edit().remove("cookies").apply()
    }
}

