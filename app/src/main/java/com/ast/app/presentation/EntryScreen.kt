package com.ast.app.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ast.app.MainActivity
import com.ast.app.graphs.Graph
import com.ast.app.graphs.Screen

@Composable
fun EntryScreen(navController:NavHostController){
    val (username, password, rememberMe) = getStoredCredentials(LocalContext.current)
    if (rememberMe) {
        navController.navigate(Graph.MAIN_SCREEN_PAGE) {
            popUpTo(Graph.AUTHENTICATION) {
                inclusive = true
            }
        }
    }else{
        navController.navigate(Graph.AUTHENTICATION) {
            popUpTo(Screen.ENTRY) {
                inclusive = true
            }
        }
    }
}

private fun getStoredCredentials(context: Context): Triple<String?, String?, Boolean> {
    val sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", null)
    val password = sharedPreferences.getString("password", null)
    val rememberMe = sharedPreferences.getBoolean("remember_me", false)
    return Triple(username, password, rememberMe)
}