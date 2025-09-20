package com.ast.app.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import com.ast.app.network.ApiService
import com.ast.app.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

suspend fun downloadAndOpenPdf(
    context: Context,
    fileUrl: String,
    fileName: String,
    mimeType: String = "application/pdf",
    apiService: ApiService = RetrofitClient.apiService
) {
    withContext(Dispatchers.IO) {
        try {
            val response = apiService.downloadPdf(fileUrl)
            if (response.isSuccessful && response.body() != null) {
                val inputStream = response.body()!!.byteStream()
                val outputFile = File(context.cacheDir, fileName)

                FileOutputStream(outputFile).use { output ->
                    inputStream.copyTo(output)
                }
                inputStream.close()

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    outputFile
                )

                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, mimeType)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                withContext(Dispatchers.Main) {
                    context.startActivity(intent)
                }
            } else {
                Log.e("PDF", "Download failed: ${response.code()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}