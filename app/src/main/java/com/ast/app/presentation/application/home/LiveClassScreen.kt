package com.ast.app.presentation.application.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ast.app.navigation.AstAppTopAppBar
import com.ast.app.navigation.AstBottomNavBar
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.common.EmptyScreen

@Composable
fun LiveClassScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        EmptyScreen()
    }

}