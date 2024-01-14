package com.ast.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    FOR_YOU(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
//        TODO: add iconTextId and titleTextId from string resource or other
        iconTextId = 1,
        titleTextId = 2,
    )
}