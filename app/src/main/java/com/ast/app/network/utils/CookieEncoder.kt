package com.ast.app.network.utils

import okhttp3.Cookie

object CookieEncoder {
    fun encode(cookie: Cookie): String =
        "${cookie.name}|${cookie.value}|${cookie.domain}|${cookie.path}"

    fun decode(data: String): Cookie? {
        val parts = data.split("|")
        return if (parts.size == 4) {
            Cookie.Builder()
                .name(parts[0])
                .value(parts[1])
                .domain(parts[2])
                .path(parts[3])
                .build()
        } else null
    }
}
