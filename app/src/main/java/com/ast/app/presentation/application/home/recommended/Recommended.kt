package com.ast.app.presentation.application.home.recommended

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.shop.CourseCard

@Composable
fun Recommended(
    navController: NavController,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Latest courses",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
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

@Composable
fun RecommendedPager(
    navController: NavController,
    recommendedViewModel: RecommendedViewModel = viewModel(),
) {
    val uiState by recommendedViewModel.recommendedUiState.collectAsState()

    val recommendedCourses = (uiState as RecommendedUiState.Success).courses

    Box {
        HorizontalPager(
            state = rememberPagerState(pageCount = { recommendedCourses!!.size }),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp,
        ) { page ->
            if (recommendedCourses != null) {
                val course = recommendedCourses[page]
                CourseCard(
                    courseId = course.id,
                    name = course.name,
                    description = course.description,
                    price = course.price,
                    discount = course.discount,
                    images = course.images,
                    category = course.category,
                    navController = navController
                )
            }
        }
    }
}
