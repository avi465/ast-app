package com.ast.app.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingButtonUsage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var loading by remember { mutableStateOf(false) }
        LoadingButton(
            onClick = { loading = true },
            loading = loading,
        )
        Button(
            onClick = { loading = false },
            enabled = loading,
        ) {
            Text(text = "Stop loading")
        }
    }
}

@Preview
@Composable
private fun LoadingButtonUsagePreview() {
    LoadingButtonUsage()
}