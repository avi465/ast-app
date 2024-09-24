package com.ast.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCartCheckout
import androidx.compose.material.icons.outlined.Tune
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

        TopAppBar(
            title = {
                Text(
                    text = "AST(beta)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
            modifier = modifier.shadow(elevation = 2.dp),
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                id = R.string.back_button
//                            )
//                        )
//                    }
//                }
//            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Outlined.NotificationsActive,
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

    // if screen is shop screen
    if (TopLevelDestination.Store.route == currentDestination?.route) {
        TopAppBar(
            modifier = modifier.shadow(elevation = 2.dp),
            title = {
                Text(
                    text = "Store",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                id = R.string.back_button
//                            )
//                        )
//                    }
//                }
//            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = {
                    navController.navigate(CartScreen.Checkout.route)
                }) {
                    Icon(
                        Icons.Outlined.ShoppingCart,
                        contentDescription = null,
                    )
                }
            }
        )
    }

    // if screen is live class screen
    if (TopLevelDestination.LiveClass.route == currentDestination?.route) {
        TopAppBar(
            modifier = modifier.shadow(elevation = 2.dp),
            title = {
                Text(
                    text = "Live CLass",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                id = R.string.back_button
//                            )
//                        )
//                    }
//                }
//            },
            actions = {
//                IconButton(onClick = { /* do something */ }) {
//                    Icon(
//                        Icons.Outlined.Tune,
//                        contentDescription = null,
//                    )
//                }
            }
        )
    }

    // if screen is my course screen
    if (TopLevelDestination.MyCourse.route == currentDestination?.route) {
        TopAppBar(
            modifier = modifier.shadow(elevation = 2.dp),
            title = {
                Text(
                    text = "My Course",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    ),
                )
            },
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                id = R.string.back_button
//                            )
//                        )
//                    }
//                }
//            },
            actions = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Outlined.Search,
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
//            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                id = R.string.back_button
//                            )
//                        )
//                    }
//                }
//            },
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

