package com.ast.app.presentation.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Composable
fun PdfReaderScreen() {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote("https://myreport.altervista.org/Lorem_Ipsum.pdf"),
        isZoomEnable = true
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}

suspend fun downloadPdfFromUrl(context: Context, pdfUrl: String): File? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(pdfUrl)
            val connection = url.openConnection()
            connection.connect()

            val input = BufferedInputStream(url.openStream(), 8192)
            val file = File.createTempFile("temp_pdf", ".pdf", context.cacheDir)

            val output = FileOutputStream(file)
            val data = ByteArray(1024)
            var total: Int

            while (input.read(data).also { total = it } != -1) {
                output.write(data, 0, total)
            }

            output.flush()
            output.close()
            input.close()

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}