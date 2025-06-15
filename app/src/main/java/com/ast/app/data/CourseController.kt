package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.Course
import com.ast.app.network.RetrofitClient

suspend fun getAllCourses(): ApiResponse<List<Course>> {
    return try {
        val response = RetrofitClient.apiService.getCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getCourseDetails(courseId: String): ApiResponse<Course> {
    return try {
        val response = RetrofitClient.apiService.getCourseById(courseId)
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getAllRecommendedCourses(): ApiResponse<List<Course>> {
    return try {
        val response = RetrofitClient.apiService.getRecommendedCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getAllPurchasedCourses(): ApiResponse<List<Course>> {
    return try {
        val response = RetrofitClient.apiService.getPurchasedCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}