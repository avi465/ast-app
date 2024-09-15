package com.ast.app.data

import android.util.Log
import com.ast.app.model.Course
import com.ast.app.network.RetrofitClient

suspend fun getAllCourses(): List<Course>? {
    return try {
        val response = RetrofitClient.apiService.getCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}