package com.ast.app.presentation.application.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ast.app.graphs.MyCourseDetailsScreen
import com.ast.app.model.Course
import com.ast.app.network.RESOURCE_ENDPOINT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCourseScreen(
    modifier: Modifier = Modifier,
    myCourseViewModel: MyCourseViewModel = viewModel(),
    navController: NavController
) {
    val uiState by myCourseViewModel.myCourseUiState.collectAsState()
    val isRefreshing by myCourseViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            myCourseViewModel.getPurchasedCourses()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is MyCourseUiState.Error -> {
                    val error = (uiState as MyCourseUiState.Error).error
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = error)
                        TextButton(onClick = { myCourseViewModel.getPurchasedCourses() }) {
                            Text(text = "Reload")
                        }
                    }
                }

                is MyCourseUiState.Success -> {
                    val courses = (uiState as MyCourseUiState.Success).courses

                    if (courses.isNullOrEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillParentMaxSize(), // fill the entire LazyColumn space
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(text = "You haven't purchased any course")
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(courses) { course ->
                                MyCourse(
                                    course,
                                    navController = navController
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun MyCourse(
    course: Course,
    navController: NavController,
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(MyCourseDetailsScreen.MyCourseDetails.route + "/${course.id}")
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            // Course Thumbnail
            if (course.images.isNotEmpty()) {
                AsyncImage(
                    model = RESOURCE_ENDPOINT + course.images[0].url + "_landscapeSM.webp",
                    contentDescription = course.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }else{
                Box(
                    modifier = Modifier
                        .width(112.dp)
                        .height(112.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Course Info
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = course.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Status: ${course.status}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}







