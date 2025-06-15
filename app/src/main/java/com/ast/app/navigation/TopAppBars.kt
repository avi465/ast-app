package com.ast.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ast.app.R
import com.ast.app.graphs.CartScreen
import com.ast.app.graphs.CourseDetailsScreen
import com.ast.app.graphs.MyCourseDetailsScreen
import com.ast.app.navigation.appbar.CourseDetailsTopAppbar
import com.ast.app.navigation.appbar.HomeTopAppbar
import com.ast.app.navigation.appbar.LiveClassTopAppbar
import com.ast.app.navigation.appbar.MyCourseDetailsTopAppbar
import com.ast.app.navigation.appbar.MyCourseTopAppbar
import com.ast.app.navigation.appbar.StoreTopAppbar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun OnBoardTopAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
//    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    MediumTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
//        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstAppTopAppBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navController: NavHostController
) {
    val screens = listOf(
        TopLevelDestination.Home,
        TopLevelDestination.LiveClass,
        TopLevelDestination.MyCourse,
        TopLevelDestination.Store,
//        TopLevelDestination.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val topAppBarDestination = screens.any { it.route == currentDestination?.route }

//    if(topAppBarDestination == currentDestination?.route)

    // if screen is home screen
    if (TopLevelDestination.Home.route == currentDestination?.route) {
        HomeTopAppbar()
    }

    // if screen is shop screen
    if (TopLevelDestination.Store.route == currentDestination?.route) {
        StoreTopAppbar()
    }

    // if screen is live class screen
    if (TopLevelDestination.LiveClass.route == currentDestination?.route) {
        LiveClassTopAppbar()
    }

    // if screen is my course screen
    if (TopLevelDestination.MyCourse.route == currentDestination?.route) {
        MyCourseTopAppbar()
    }

    // if screen is course details screen
    val isCourseDetailsScreen = currentDestination?.route
        ?.startsWith(CourseDetailsScreen.CourseDetails.route) == true
    if (isCourseDetailsScreen) {
        CourseDetailsTopAppbar(navigateUp = navigateUp)
    }

    // if screen is my course details screen
    val isMyCourseDetailsScreen = currentDestination?.route
        ?.startsWith(MyCourseDetailsScreen.MyCourseDetails.route) == true
    if (isMyCourseDetailsScreen) {
        MyCourseDetailsTopAppbar(navigateUp = navigateUp)
    }

    // if screen is account screen
    if (TopLevelDestination.Settings.route == currentDestination?.route) {
        TopAppBar(
            modifier = modifier.shadow(elevation = 2.dp),
            title = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_button
                            )
                        )
                    }
                }
            },
            actions = {
            }
        )
    }

    // if screen is cart screen
    if (CartScreen.Checkout.route == currentDestination?.route) {
        TopAppBar(
            title = {
                Text(
                    text = "My Cart",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
            modifier = modifier.shadow(elevation = 2.dp),
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            },
        )
    }
}

