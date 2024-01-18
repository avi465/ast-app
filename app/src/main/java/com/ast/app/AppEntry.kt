package com.ast.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ast.app.navigation.AstNavigation
import com.ast.app.presentation.auth.onboarding.Onboard


@Composable
fun AppEntry(modifier: Modifier = Modifier) {
//    Onboard()
    AstNavigation()
}