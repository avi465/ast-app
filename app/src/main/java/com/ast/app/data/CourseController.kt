package com.ast.app.data

import android.util.Log
import com.ast.app.model.ApiResponse
import com.ast.app.model.Course
import com.ast.app.model.Lesson
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun getAllCourses(): Response<ApiResponse<List<Course>>> {
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

suspend fun getAllRecommendedCourses(): Response<ApiResponse<List<Course>>> {
    return try {
        val response = RetrofitClient.apiService.getRecommendedCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getAllPurchasedCourses(): Response<ApiResponse<List<Course>>> {
    return try {
        val response = RetrofitClient.apiService.getPurchasedCourses()
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getCourseLessons(courseId: String): Response<ApiResponse<List<Lesson>>> {
    return try {
        val response = RetrofitClient.apiService.getCourseLessons(courseId)
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}

suspend fun getLessonById(lessonId: String): Response<ApiResponse<Lesson>> {
    return try {
        val response = RetrofitClient.apiService.getLessonById(lessonId)
        response
    } catch (e: Exception) {
        // Handle exceptions gracefully (e.g., log them, display error messages)
        error(message = e)
    }
}