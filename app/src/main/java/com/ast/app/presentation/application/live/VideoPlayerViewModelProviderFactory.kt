package com.ast.app.presentation.application.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VideoPlayerViewModelProviderFactory(private val lessonId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)) {
            return VideoPlayerViewModel(lessonId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}