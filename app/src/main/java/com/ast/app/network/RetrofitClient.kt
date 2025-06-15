package com.ast.app.network

import com.ast.app.network.utils.CookieManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

//private const val API_ENDPOINT = "https://ast-qbnh4.ondigitalocean.app"
private const val API_ENDPOINT = "https://29042a9e48ba-7364538330906752083.ngrok-free.app"
const val IMAGE_RESOURCE_ENDPOINT = "$API_ENDPOINT/"
private const val BASE_URL = "$API_ENDPOINT/api/"

val okHttpClient = OkHttpClient.Builder()
    .cookieJar(CookieManager.get())
    .build()

val json = Json {
    ignoreUnknownKeys = true // 👈 Important!
    isLenient = true
}

object RetrofitClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}