package com.ast.app.presentation.application.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil3.compose.AsyncImage
import com.ast.app.R
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.navigation.TopLevelDestination
import com.ast.app.presentation.application.home.recommended.Recommended
import com.ast.app.presentation.application.home.recommended.RecommendedUiState
import com.ast.app.presentation.application.home.recommended.RecommendedViewModel
import com.ast.app.presentation.common.BannerPager
import com.ast.app.presentation.common.CircularLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    recommendedViewModel: RecommendedViewModel = viewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val uiState by recommendedViewModel.recommendedUiState.collectAsState()
    val isRefreshing by recommendedViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            recommendedViewModel.getRecommendedCourses()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is RecommendedUiState.Success -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = modifier
                            .verticalScroll(state = scrollState, enabled = true)
                    ) {
                        BannerPager()
                        QuickAccess(modifier = Modifier, navController = navController)
                        // LiveClassCard(navController = navController)
                        // DailyQuizCard()
                        Recommended(navController = navController)
                        // WhyChooseUsSection()
                        /* ScholarshipReferralCard(
                            onClick = { /* navigate to referral screen */ }
                        ) */
                    }
                }

                is RecommendedUiState.Error -> {
                    val error = (uiState as RecommendedUiState.Error).error
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = error)
                        TextButton(onClick = {
                            recommendedViewModel.getRecommendedCourses()
                        }) {
                            Text(text = "Reload")
                        }
                    }
                }

                RecommendedUiState.Loading -> { }
            }
        }
    }
}

@Composable
fun QuickAccess(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        QuickAccessItem("Live Class", R.drawable.satellite_solid),
        QuickAccessItem("Test Series", R.drawable.hourglass_half_solid),
        QuickAccessItem("Downloads", R.drawable.file_arrow_down_solid),
        QuickAccessItem("Notes", R.drawable.book_open_solid)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            QuickAccessCard(
                navController = navController,
                item = item,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

data class QuickAccessItem(
    val title: String,
    val iconRes: Int,
)

@Composable
fun QuickAccessCard(
    navController: NavController,
    item: QuickAccessItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clickable {
                    if (item.title == "Live Class") {
                        navController.navigate(TopLevelDestination.LiveClass.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
//            colors = CardDefaults.cardColors()
//                .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f))
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = item.iconRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            text = item.title,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
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
                text = "Live class",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
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
//            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
//            colors = CardDefaults.elevatedCardColors(containerColor = Color.Transparent)
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
                        .height(164.dp)
                        .align(Alignment.Center)
                )
//                LiveLabel(modifier = Modifier.align(alignment = Alignment.BottomStart))
                Icon(
                    imageVector = Icons.Filled.PlayCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(56.dp)
                        .padding(8.dp)
                )
            }

            ListItem(
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                overlineContent = { Text(text = "PHYSICS") },
                headlineContent = {
                    Text(
                        text = "KINEMATICS",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                supportingContent = {
                    Column {
                        Text(
                            text = "Avinash Karmjit",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        Text(
                            text = "Started 38 min ago",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
            )

        }
    }
}

@Composable
fun WhyChooseUsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Heading
        Text(
            text = "Why Choose Us?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Join a trusted learning ecosystem built for your success.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Stats Grid
        StatsGrid()

        Spacer(modifier = Modifier.height(32.dp))

        // Testimonials
        Testimonials()

        Spacer(modifier = Modifier.height(32.dp))

        // Image Gallery
        InfrastructureImages()
    }
}

@Composable
fun StatsGrid() {
    val stats = listOf(
        Triple(
            "95% Success Rate",
            Icons.Default.EmojiEvents,
            "Students consistently perform well."
        ),
        Triple("10K+ Students", Icons.Default.People, "Trusted by thousands across India."),
        Triple("50+ Mentors", Icons.Default.School, "Experienced & caring teachers.")
    )

    Column {
        stats.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { (title, icon, desc) ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = title, fontWeight = FontWeight.Bold)
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Testimonials() {
    val testimonials = listOf(
        Testimonial(
            "Amit Sharma",
            "Cleared NEET in my first attempt thanks to expert faculty.",
            "https://randomuser.me/api/portraits/men/32.jpg"
        ),
        Testimonial(
            "Priya Verma",
            "The structured courses and mock tests helped me a lot.",
            "https://randomuser.me/api/portraits/women/45.jpg"
        )
    )

    Column {
        Text(
            "Student Testimonials",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        testimonials.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = it.imageUrl,
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = it.name, fontWeight = FontWeight.Bold)
                        Text(text = "\"${it.text}\"", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun InfrastructureImages() {
    val images = listOf(
        "https://yourdomain.com/classroom1.jpg",
        "https://yourdomain.com/classroom2.jpg",
        "https://yourdomain.com/classroom3.jpg"
    )

    Text(
        "Our Campus",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(images) { imageUrl ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(width = 220.dp, height = 140.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Campus Image",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun DailyQuizCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Quiz, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Daily Practice", fontWeight = FontWeight.Medium)
                Text("Solve 10 questions now", color = Color.Gray)
            }
        }
    }
}

@Composable
fun ScholarshipReferralCard(
    modifier: Modifier = Modifier,
    title: String = "Earn ₹500 by Referring!",
    subtitle: String = "Invite your friends and both of you get exciting rewards.",
    buttonText: String = "Refer Now",
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}


// Model
data class Testimonial(val name: String, val text: String, val imageUrl: String)
