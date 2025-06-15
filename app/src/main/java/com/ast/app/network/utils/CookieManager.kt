package com.ast.app.network.utils

import android.content.SharedPreferences

object CookieManager {
    private var cookieJar: PersistentCookieJar? = null

    fun init(sharedPreferences: SharedPreferences) {
        cookieJar = PersistentCookieJar(sharedPreferences)
    }

    fun get(): PersistentCookieJar {
        return cookieJar ?: throw IllegalStateException("CookieManager not initialized")
    }

    fun clear() = cookieJar?.clear()
}

