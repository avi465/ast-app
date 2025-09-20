package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.SlideModel
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun getCourseSlides(courseId: String): Response<ApiResponse<List<SlideModel>>> {
    return RetrofitClient.apiService.getCourseSlides(courseId)
}