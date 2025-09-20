package com.ast.app.presentation.application.live

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ast.app.model.Lesson
import com.ast.app.presentation.application.live.videoplayerutils.VideoPlayer
import com.ast.app.presentation.common.CircularLoader
import com.ast.app.utils.UiState
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VideoPlayerScreen(
    navController: NavHostController,
    lessonId: String,
    videoPlayerViewModel: VideoPlayerViewModel = viewModel(
        factory = VideoPlayerViewModelProviderFactory(lessonId = lessonId)
    )
) {
    val lessonState by videoPlayerViewModel.lessonState.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity
    var isPlayerVisible by remember { mutableStateOf(true) }
    var isFullScreen by rememberSaveable { mutableStateOf(false) }

    // todo: validate if the passed URL to this component is valid and accessible
    val swipeGestureModifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, _, _ ->
                if (pan.y > 0) {
                    // Swipe Down: exit fullscreen
                    if (isFullScreen) {
                        exitFullscreen(activity)
                        isFullScreen = false
                    }
                } else if (pan.y < 0) {
                    // Swipe Up: enter fullscreen
                    if (!isFullScreen) {
                        enterFullscreen(activity)
                        isFullScreen = true
                    }
                }
            }
        }

    when (lessonState) {
        is UiState.Success -> {
            val lesson = (lessonState as UiState.Success).data
            val videoUrl = lesson.videoUrl
            if (videoUrl.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No video/stream available for this lesson.",
                    )
                    TextButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Go Back",
                        )
                    }
                }
            } else if (isPlayerVisible) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (isFullScreen) {
                        enterFullscreen(activity)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        ) {
                            VideoPlayer(
                                modifier = Modifier,
                                navController = navController,
                                url = videoUrl,
                                isFullScreen = isFullScreen,
                                onPlayerReleased = { isPlayerVisible = false },
                                onFullScreenToggle = { isFullScreen = !isFullScreen }
                            )
                        }
                    } else {
                        exitFullscreen(activity)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .background(Color.Black)
                        ) {
                            VideoPlayer(
                                modifier = Modifier,
                                navController = navController,
                                url = videoUrl,
                                isFullScreen = isFullScreen,
                                onPlayerReleased = { isPlayerVisible = false },
                                onFullScreenToggle = { isFullScreen = !isFullScreen }
                            )
                        }
                        VideoPlayerScreenDetailsCard(lesson = lesson)

                        val chatMessages = listOf(
                            ChatMessage(1, "Alice", "Hey, are you joining the session?", false),
                            ChatMessage(2, "You", "Yeah, just got in!", true),

                            ChatMessage(4, "You", "Sure, it was about projectile motion.", true),
                            ChatMessage(5, "Alice", "Omg 🤯 that was hard to follow!", false),
                            ChatMessage(6, "You", "Don’t worry, we’ll go through it again.", true),
                            ChatMessage(10, "Bob", "Thanks bro 🙌", false),
                            ChatMessage(11, "You", "No problem 😎", true),
                            ChatMessage(12, "Alice", "Also, there's a quiz tomorrow right?", false),
                            ChatMessage(
                                13,
                                "You",
                                "Yes, on Chapter 5. Better revise tonight 😅",
                                true
                            ),
                            ChatMessage(
                                14,
                                "Bob",
                                "Wait what?! I thought it was next week 😭",
                                false
                            ),
                            ChatMessage(15, "You", "Nope. Surprise! 😂", true),
                            ChatMessage(16, "Alice", "This group is a lifesaver fr 💯", false),
                            ChatMessage(17, "Alice", "Yo", false),
                            ChatMessage(18, "You", "Haha we got each other's backs 💪", true),
                        )


                Box(modifier = Modifier.weight(1f)) {
                    LiveChatComposable(
                        messages = chatMessages,
                        onSend = { }
                    )
                }
                    }
                }
            }
        }

        is UiState.Error -> {
            val errorMessage = (lessonState as UiState.Error).error
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error: $errorMessage",
                )
                TextButton(onClick = {
                    videoPlayerViewModel.fetchLessonByLessonId(lessonId)
                }) {
                    Text(
                        text = "Retry",
                    )
                }
            }
        }

        is UiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularLoader()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
private fun enterFullscreen(activity: Activity?) {
    activity?.window?.insetsController?.apply {
        hide(WindowInsets.Type.systemBars()) // Hide status and navigation bars
        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
}

@SuppressLint("SourceLockedOrientationActivity")
@RequiresApi(Build.VERSION_CODES.R)
private fun exitFullscreen(activity: Activity?) {
    activity?.window?.insetsController?.apply {
        show(WindowInsets.Type.systemBars()) // Show status and navigation bars
    }
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

@Composable
fun VideoPlayerScreenDetailsCard(lesson: Lesson) {
    Card(
        shape = RectangleShape,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = lesson.title.replaceFirstChar { it.uppercase(Locale.getDefault()) },
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = lesson.description?.replaceFirstChar { it.uppercase(Locale.getDefault()) }
                    ?: "No description available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LiveChatComposable(
    messages: List<ChatMessage>,
    onSend: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            reverseLayout = true
        ) {
            itemsIndexed(messages.reversed()) { index, message ->
                val nextMessage = messages.getOrNull(messages.size - index - 2)
                val isNextSameSender = nextMessage?.sender == message.sender
                val showSender = !isNextSameSender

                ChatBubble(
                    message = message.message,
                    sender = message.sender,
                    isOwnMessage = message.isOwnMessage,
                    showSender = showSender
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                decorationBox = { innerTextField ->
                    if (input.isEmpty()) {
                        Text(
                            "Type a message...",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            IconButton(onClick = {
                if (input.isNotBlank()) {
                    onSend(input)
                    input = ""
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: String,
    sender: String,
    isOwnMessage: Boolean,
    showSender: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalAlignment = if (isOwnMessage) Alignment.End else Alignment.Start
    ) {
        if (showSender) {
            Text(
                text = sender,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
            )
        }
        Text(
            text = message,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
//                    color = if (isOwnMessage) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
//                    else MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

data class ChatMessage(
    val id: Int,
    val sender: String,
    val message: String,
    val isOwnMessage: Boolean
)


