package com.ast.app.navigation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.ImportContacts
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ast.app.AstNavGraph
import com.ast.app.R

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String
) {
    HOME(
        selectedIcon = Icons.Rounded.Folder,
        unselectedIcon = Icons.Outlined.Folder,
        iconText = "home",
        titleText = "Home"
    ),
    LIVE_CLASS(
        selectedIcon = Icons.Rounded.Sensors,
        unselectedIcon = Icons.Outlined.Sensors,
        iconText = "live_class",
        titleText = "Live Class"
    ),
    MY_CLASS(
        selectedIcon = Icons.Rounded.ImportContacts,
        unselectedIcon = Icons.Outlined.ImportContacts,
        iconText = "my_class",
        titleText = "My Class"
    ),
    ASK_DOUBT(
        selectedIcon = Icons.Rounded.Draw,
        unselectedIcon = Icons.Outlined.Draw,
        iconText = "ask_doubt",
        titleText = "Ask Doubt"
    )
}

//@Composable
//fun AstNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
//    // changing the navigation bar color to match the bottom navigation bar
//    val view = LocalView.current
//    val window = (view.context as Activity).window
//    window.navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp).toArgb()
//
//    //Get current back stack entry
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    //Get the name of current screen
//    val currentRoute = TopLevelDestination.valueOf(
//        navBackStackEntry?.destination?.route ?: TopLevelDestination.HOME.name
//    )
//
//    Scaffold(
//        modifier = modifier,
//        topBar = {
//            AstAppTopAppBar(
//                canNavigateBack = navController.previousBackStackEntry != null,
//                navigateUp = { navController.navigateUp() },
//            )
//        },
//        bottomBar = {
//            AstBottomNavBar(modifier = Modifier, navController, currentRoute)
//        }
//    ) { innerPadding ->
//        AppNavGraph(
//            navController = navController,
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstBottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: TopLevelDestination,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        TopLevelDestination.entries.forEach { item ->
            NavigationBarItem(
                selected = currentRoute.toString() == item.name,
                onClick = {
                    navController.navigate(item.name) {
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
                            imageVector = if (currentRoute.toString() == item.name) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.iconText
                        )
                    }
                })
        }
    }
}