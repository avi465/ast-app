package com.ast.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.ImportContacts
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.rounded.Draw
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material.icons.rounded.ImportContacts
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ast.app.R
import com.ast.app.presentation.application.home.AskDoubtScreen
import com.ast.app.presentation.application.home.HomeScreen
import com.ast.app.presentation.application.home.LiveClassScreen
import com.ast.app.presentation.application.home.MyClassScreen

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

@Composable
fun AstNavigation(modifier: Modifier = Modifier) {
    //create NavController
    val navController = rememberNavController()
    //Get current back stack entry
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    //Get the name of current screen
    val currentRoute = TopLevelDestination.valueOf(
        navBackStackEntry?.destination?.route ?: TopLevelDestination.HOME.name
    )

    Scaffold(
        topBar = {
            AstAppTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
        bottomBar = {
            AstNavigationBar(modifier = Modifier, navController, currentRoute)
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstAppTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.Bookmarks,
                    contentDescription = null,
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = null,
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: TopLevelDestination,
    ) {
    NavigationBar(
        modifier = modifier
    ) {
        TopLevelDestination.entries.forEach { item ->
            NavigationBarItem(
                selected = currentRoute.toString() == item.name,
                onClick = {
                    navController.navigate(item.name){
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
                            contentDescription = null
                        )
                    }
                })
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.name,
        modifier = modifier
    ) {
        composable(route = TopLevelDestination.HOME.name) {
            HomeScreen()
        }
        composable(route = TopLevelDestination.LIVE_CLASS.name) {
            LiveClassScreen()
        }

        composable(route = TopLevelDestination.MY_CLASS.name) {
            MyClassScreen()
        }

        composable(route = TopLevelDestination.ASK_DOUBT.name) {
            AskDoubtScreen()
        }
    }
}