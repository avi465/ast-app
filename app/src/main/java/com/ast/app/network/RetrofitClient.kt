package com.ast.app.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

//private const val API_ENDPOINT = "https://ast-qbnh4.ondigitalocean.app"
private const val API_ENDPOINT = "https://f127-152-59-128-255.ngrok-free.app"
private const val BASE_URL = "$API_ENDPOINT/api/"

object RetrofitClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}