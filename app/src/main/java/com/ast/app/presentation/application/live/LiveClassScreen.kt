package com.ast.app.presentation.application.live

import android.graphics.drawable.VectorDrawable
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.presentation.common.LiveLabel

@OptIn(UnstableApi::class)
@Composable
fun LiveClassScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    //filter chips state
    var selectedAll by remember { mutableStateOf(true) }
    var selectedLive by remember { mutableStateOf(false) }
    var selectedScheduled by remember { mutableStateOf(false) }

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            FilterChip(
                onClick = {
                    selectedAll = !selectedAll
                    selectedLive = false
                    selectedScheduled = false
                },
                label = {
                    Text("All")
                },
                selected = selectedAll,
                leadingIcon = if (selectedAll) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
            FilterChip(
                onClick = {
                    selectedLive = !selectedLive
                    selectedScheduled = false
                    selectedAll = false
                },
                label = {
                    Text("Live")
                },
                selected = selectedLive,
                leadingIcon = if (selectedLive) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
            FilterChip(
                onClick = {
                    selectedScheduled = !selectedScheduled
                    selectedLive = false
                    selectedAll = false
                },
                label = {
                    Text("Scheduled")
                },
                selected = selectedScheduled,
                leadingIcon = if (selectedScheduled) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
        if (selectedAll) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    LiveClassCard(navController = navController)
                }
            }
        } else if (selectedLive) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No Live Class",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else if (selectedScheduled) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "No Scheduled Class",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

//    var selectedTabIndex by remember { mutableIntStateOf(0) }
//    val tabItems = listOf("All", "Live", "Schedule", "Completed")
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        ScrollableTabRow(
//            selectedTabIndex = selectedTabIndex,
//            edgePadding = 16.dp,
//        ) {
//            tabItems.forEachIndexed { index, item ->
//                Tab(
//                    selected = index == selectedTabIndex,
//                    onClick = { selectedTabIndex = index },
//                    text = { Text(text = item) },
//                    unselectedContentColor = MaterialTheme.colorScheme.onSurface
//                )
//            }
//        }
//
//
//        if (selectedTabIndex == 0) {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                contentPadding = PaddingValues(bottom = 16.dp)
//            ) {
//                item {
//                    LiveClassCard(navController = navController)
//                }
//                item {
//                    LiveClassCard(navController = navController)
//                }
//            }
//        } else if (selectedTabIndex == 1) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Text(
//                    text = "This feature is being developed",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        } else if (selectedTabIndex == 2) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Text(
//                    text = "This feature is being developed",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        }
//    }
}

@OptIn(UnstableApi::class)
@Composable
fun LiveClassCard(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ElevatedCard(
            Modifier
                .clickable {
                    navController.navigate(LiveClassScreen.LiveClassPlayer.route)
                },
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            shape = RectangleShape
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
                LiveLabel(modifier = Modifier.align(Alignment.BottomEnd))
            }

            LiveClassCardDetails()
        }
    }
}

@Composable
fun LiveClassCardDetails() {
    ListItem(
        modifier = Modifier.padding(bottom = 8.dp),
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
        ),
        leadingContent = {
            Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "icon")
        },
        overlineContent = { Text(text = "BANK/SSC/RAILWAY") },
        headlineContent = { Text(text = "Surface Tension - Physics") },
        supportingContent = { Text(text = "In this lecture we are going to learn about competitive exam pattern") },
    )
}