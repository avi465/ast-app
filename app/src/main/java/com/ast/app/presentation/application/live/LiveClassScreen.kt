package com.ast.app.presentation.application.live

import androidx.annotation.OptIn
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ast.app.R
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.model.StreamModel
import com.ast.app.network.RESOURCE_ENDPOINT
import com.ast.app.presentation.common.LiveLabel
import com.ast.app.utils.UiState
import java.net.URLEncoder

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveClassScreen(
    navController: NavController,
    liveClassViewModel: LiveClassScreenViewModel = viewModel(),
) {
    //filter chips state
    var selectedAll by remember { mutableStateOf(true) }
    var selectedLive by remember { mutableStateOf(false) }
    var selectedScheduled by remember { mutableStateOf(false) }

    val streamState by liveClassViewModel.streamState.collectAsState()
    val isRefreshing by liveClassViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            liveClassViewModel.fetchStreamForUser()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                    when (streamState) {
                        is UiState.Success -> {
                            val streams = (streamState as UiState.Success<List<StreamModel>>).data
                            if (streams.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(bottom = 16.dp)
                                ) {
                                    items(streams) { stream ->
                                        LiveClassCard(
                                            navController = navController,
                                            stream = stream
                                        )
                                    }
                                }
                            } else {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "No Live Classes",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }

                        is UiState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = (streamState as UiState.Error).error,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                TextButton(onClick = {
                                    liveClassViewModel.fetchStreamForUser()
                                }) {
                                    Text(text = "Reload")
                                }
                            }
                        }

                        is UiState.Loading -> {}
                    }


                } else if (selectedLive) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Not implemented yet",
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
                            text = "Not implemented yet",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun LiveClassCard(navController: NavController, stream: StreamModel) {
    var imageUrl = "";
    if (stream.lesson.images.isNotEmpty()) {
        imageUrl = RESOURCE_ENDPOINT + "upload/images/" + URLEncoder.encode(stream.lesson.images[0], "utf-8") + "_landscapeSM.webp"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ElevatedCard(
            Modifier
                .clickable {
                    val lessonId = stream.lesson.id
                    navController.navigate(LiveClassScreen.LiveClassPlayer.route + "/${lessonId}") {
                        launchSingleTop = true
                    }
                },
            shape = RectangleShape,
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            colors = CardDefaults.elevatedCardColors(Color.Transparent)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (imageUrl.isNotEmpty()){
                    AsyncImage(
                        model = RESOURCE_ENDPOINT + "upload/images/e3061db6-87af-498b-b5d7-0413ff097378" + "_landscapeSM.webp",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(196.dp)
                            .align(Alignment.Center)
                    )
                }else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(196.dp)
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

                LiveLabel(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    status = stream.lesson.status
                )
            }

            LiveClassCardDetails(stream = stream)
        }
    }
}

@Composable
fun LiveClassCardDetails(stream: StreamModel) {
    ListItem(
        modifier = Modifier.padding(bottom = 8.dp),
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        leadingContent = {
            Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "icon")
        },
        overlineContent = { Text(text = "BANK/SSC/RAILWAY") },
        headlineContent = { Text(text = stream.lesson.title) },
        supportingContent = { stream.lesson.description?.let { Text(text = it) } },
    )
}