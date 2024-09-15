package com.ast.app.presentation.application.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.DownloadDone
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ast.app.R
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.shop.CourseCard
import com.ast.app.presentation.common.BannerPager
import com.ast.app.presentation.common.LiveLabel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = modifier
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            BannerPager()
            QuickAccess(modifier = Modifier, navController = navController)
            LiveClassCard(navController = navController)
            Recommended(navController = navController)
            Feedback()
        }
    }

}

@Composable
fun QuickAccess(navController: NavController, modifier: Modifier) {
    val list = listOf(
        Icons.Outlined.Sensors,
        Icons.Outlined.Draw,
        Icons.Outlined.DownloadDone,
        Icons.Outlined.Book
    )

    val title = listOf(
        "Live Class",
        "Test Series",
        "Downloads",
        "Resources"
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            QuickAccessCard(navController = navController, icon = list[0], title = title[0])
            QuickAccessCard(navController = navController, icon = list[1], title = title[1])
            QuickAccessCard(navController = navController, icon = list[2], title = title[2])
            QuickAccessCard(navController = navController, icon = list[3], title = title[3])
        }
    }
}

@Composable
fun QuickAccessCard(navController: NavController, icon: ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable {
                if (title == "Live Class") {
                    navController.navigate(TopLevelDestination.LiveClass.route) {
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
                }
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .size(29.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
fun LiveClassCard(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "LIVE CLASS",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = {
//                navController.navigate(TopLevelDestination.LiveClass.route)
                navController.navigate(TopLevelDestination.LiveClass.route) {
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
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                )
            }
        }

        ElevatedCard(
            Modifier
                .clickable {
                    navController.navigate(LiveClassScreen.LiveClassPlayer.route)
                }
                .padding(horizontal = 16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.course_img1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(148.dp)
                        .align(Alignment.Center)
                )
                LiveLabel(modifier = Modifier.align(alignment = Alignment.BottomEnd))
            }

            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent,
                ),
                overlineContent = { Text(text = "BANK/SSC/RAILWAY") },
                headlineContent = { Text(text = "Surface Tension - Physics") },
                supportingContent = { Text(text = "In this lecture we are going to learn about competitive exam pattern") },
            )
        }
    }
}

@Composable
fun Recommended(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "LATEST COURSES",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = {
//                navController.navigate(TopLevelDestination.Store.route)
                navController.navigate(TopLevelDestination.Store.route) {
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
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = null,
                )
            }
        }
        RecommendedPager(navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendedPager(navController: NavController) {
    val imageSlider = listOf(
        painterResource(id = R.drawable.course_img1),
        painterResource(id = R.drawable.course_img1),
    )

    val pagerState = rememberPagerState(pageCount = {
        imageSlider.size
    })

    Box {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp,
        ) { page ->
            CourseCard(
                name = "Jee Main 2025",
                description = "welcome to the math course by advance study tutorials",
                price = 1000,
                discount = 70,
                img = painterResource(id = R.drawable.course_img1),
                navController = navController
            )
        }
    }
}

@Composable
fun Feedback() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .height(224.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.thanks_feedback),
            contentDescription = "feedback_banner",
            Modifier
                .height(148.dp)
                .align(
                    Alignment.BottomEnd
                ),
            contentScale = ContentScale.Fit,
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Invite friends to get ${'\u20B9'}250 off",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Invite friend to Advanced Study Tutorials and get ${'\u20B9'}250 off on their first purchase",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight(400),
                fontSize = 14.sp
            )
            val inviteCodeText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight(400),
                        fontSize = 14.sp
                    )
                ) {
                    append("Copy your invite code ")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight(600),
                        fontSize = 14.sp
                    )
                ) {
                    append("Zb67ghi8")
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = inviteCodeText,
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(imageVector = Icons.Filled.CopyAll, contentDescription = "copy", Modifier.size(20.dp))
            }


            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Invite")
            }
        }

    }
}