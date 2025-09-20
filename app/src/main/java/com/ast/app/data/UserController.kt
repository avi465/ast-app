package com.ast.app.data

import com.ast.app.model.ApiResponse
import com.ast.app.model.SlideModel
import com.ast.app.model.UserModel
import com.ast.app.network.RetrofitClient
import retrofit2.Response

suspend fun getUserProfile(): Response<ApiResponse<UserModel>> {
    return RetrofitClient.apiService.getUserProfile()
}

suspend fun updateUserProfile(userModel: UserModel): Response<ApiResponse<UserModel>> {
    return RetrofitClient.apiService.updateUserProfile(userModel)
}