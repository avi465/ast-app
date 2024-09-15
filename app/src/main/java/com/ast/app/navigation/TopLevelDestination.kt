package com.ast.app.navigation

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.ImportContacts
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String
) {
    object Home : TopLevelDestination(
        route = "HOME",
        selectedIcon = Icons.Rounded.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        iconText = "home",
        titleText = "Home"
    )

    object LiveClass : TopLevelDestination(
        route = "LIVE_CLASS",
        selectedIcon = Icons.Rounded.Sensors,
        unselectedIcon = Icons.Outlined.Sensors,
        iconText = "live_class",
        titleText = "Live Class"
    )

    object MyCourse : TopLevelDestination(
        route = "MY_COURSE",
        selectedIcon = Icons.Rounded.ImportContacts,
        unselectedIcon = Icons.Outlined.ImportContacts,
        iconText = "my_course",
        titleText = "My Course"
    )

    object Store : TopLevelDestination(
        route = "STORE",
        selectedIcon = Icons.Rounded.ShoppingBag,
        unselectedIcon = Icons.Outlined.ShoppingBag,
        iconText = "store",
        titleText = "Store"
    )

    object Settings : TopLevelDestination(
        route = "Settings",
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconText = "Settings",
        titleText = "Settings"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstBottomNavBar(navController: NavHostController) {
    // changing the navigation bar color to match the bottom navigation bar
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp).toArgb()

    val screens = listOf(
        TopLevelDestination.Home,
        TopLevelDestination.LiveClass,
        TopLevelDestination.MyCourse,
        TopLevelDestination.Store,
        TopLevelDestination.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
        ) {
            screens.forEach { item ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = item.titleText)
                    },
                    icon = {
                        BadgedBox(badge = {}) {
                            Icon(
                                imageVector = if (selected) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.iconText
                            )
                        }
                    }
                )
            }
        }
    }
}
