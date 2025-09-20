package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.Quiz
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun getCourseQuizzes(courseId: String): Response<ApiResponse<List<Quiz>>> {
    return RetrofitClient.apiService.getCourseQuizzes(courseId)
}