package com.ast.app.utils

import com.ast.app.network.RESOURCE_ENDPOINT

object UrlUtil {
    fun constructFileUrl(filePath: String): String {
        return RESOURCE_ENDPOINT + filePath
    }

    fun constructImageUrl(imagePath: String, mode: String = "_landscapeSM.webp"): String {
        return RESOURCE_ENDPOINT + imagePath + mode
    }
}