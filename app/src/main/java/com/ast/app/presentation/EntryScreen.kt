package com.ast.app.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ast.app.MainActivity
import com.ast.app.graphs.Graph
import com.ast.app.graphs.Screen
import com.ast.app.presentation.application.MainScreen
import com.ast.app.presentation.auth.SplashScreen
import com.ast.app.presentation.common.DotsTyping

@Composable
fun EntryScreen(
    navController: NavHostController
) {
    val (username, password, rememberMe) = getStoredCredentials(LocalContext.current)

    if (rememberMe) {
        MainScreen()
    } else {
        SplashScreen(navController = navController)
    }
}

private fun getStoredCredentials(context: Context): Triple<String?, String?, Boolean> {
    val sharedPreferences =
        context.getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", null)
    val password = sharedPreferences.getString("password", null)
    val rememberMe = sharedPreferences.getBoolean("remember_me", false)
    return Triple(username, password, rememberMe)
}