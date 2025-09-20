package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.SlideModel
import com.ast.app.model.StreamModel
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun getStreamForUser(): Response<ApiResponse<List<StreamModel>>> {
    return RetrofitClient.apiService.getStreamForUser()
}