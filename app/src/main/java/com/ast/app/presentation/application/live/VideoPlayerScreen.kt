package com.ast.app.presentation.application.live

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ast.app.presentation.application.live.videoplayerutils.VideoPlayer

//@Composable
//fun VideoPlayerScreen(
//    navController: NavController
//) {
//    Column(
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally,
////            modifier = Modifier.padding(it)
//    ) {
////        val url = "http://137.184.36.54:8000/live/obs_stream/index.m3u8"
//        val url =
//            "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8"
//        VideoPlayer(navController = navController, url = url)
//
////        VideoPlayerScreenDetailsCard()
////        VideoPlayerScreenChatField()
////        Spacer(modifier = Modifier.weight(1f))
////        VideoPlayerScreenChat()
//    }
//}


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VideoPlayerScreen(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as? Activity
    var isPlayerVisible by remember { mutableStateOf(true) }
    var isFullScreen by rememberSaveable { mutableStateOf(false) }
    val url = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"

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

    if (isPlayerVisible) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isFullScreen) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    VideoPlayer(
                        navController = navController,
                        url = url,
                        isFullScreen = isFullScreen,
                        onPlayerReleased = { isPlayerVisible = false },
                        onFullScreenToggle = { isFullScreen = !isFullScreen }
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(Color.Black)
                ) {
                    VideoPlayer(
                        navController = navController,
                        url = url,
                        isFullScreen = isFullScreen,
                        onPlayerReleased = { isPlayerVisible = false },
                        onFullScreenToggle = { isFullScreen = !isFullScreen }
                    )
                }
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
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

@RequiresApi(Build.VERSION_CODES.R)
private fun exitFullscreen(activity: Activity?) {
    activity?.window?.insetsController?.apply {
        show(WindowInsets.Type.systemBars()) // Show status and navigation bars
    }
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}

@Composable
fun VideoPlayerScreenDetailsCard() {
    Card(
        shape = RectangleShape,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "These coding projects gives you an unfair advantage",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Computer Programming",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun VideoPlayerScreenChat() {
    val itemCount = 140
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        items(itemCount) {
            Text(
                text = "Avinash: Hey, what's going on!",
            )
        }
    }
}

@Composable
fun VideoPlayerScreenChatField() {
    var phone by rememberSaveable {
        mutableStateOf("")
    }
    var isPhoneFieldValid by rememberSaveable {
        mutableStateOf(true)
    }

    Card(
        shape = RectangleShape,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                placeholder = {
                    Text(
                        text = "Type to chat...",
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                value = phone,
                onValueChange = {
                    phone = it
                    isPhoneFieldValid = phone.isNotBlank()
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "send"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Filled.EmojiEmotions,
//                    contentDescription = "send"
//                )
//            }
        }
    }
}
