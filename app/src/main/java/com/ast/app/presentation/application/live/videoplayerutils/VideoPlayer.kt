package com.ast.app.presentation.application.live.videoplayerutils

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import kotlin.math.max
import kotlin.math.min

//@Composable
//fun VideoPlayer(modifier: Modifier = Modifier) {
//
//    val context = LocalContext.current
//
//    // player listener
//    var totalDuration by remember { mutableStateOf(0L) }
//    var currentTime by remember { mutableStateOf(0L) }
//    var bufferedPercentage by remember { mutableStateOf(0) }
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(
//                MediaItem.fromUri(
//                    "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
//                )
//            )
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    Box(modifier = Modifier) {
//        DisposableEffect(key1 = Unit) {
//            val listener =
//                object : Player.Listener {
//                    override fun onEvents(player: Player, events: Player.Events) {
//                        super.onEvents(player, events)
//                        totalDuration = player.duration.coerceAtLeast(0L)
//                        currentTime = player.currentPosition.coerceAtLeast(0L)
//                        bufferedPercentage = player.bufferedPercentage
//                    }
//                }
//
//            exoPlayer.addListener(listener)
//
//            onDispose {
//                exoPlayer.removeListener(listener)
//                exoPlayer.release()
//            }
//        }
//    }
//
//    Box(modifier = modifier) {
//        DisposableEffect(key1 = Unit) { onDispose { exoPlayer.release() } }
//
//        AndroidView(
//            factory = {
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                    layoutParams =
//                        FrameLayout.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.MATCH_PARENT
//                        )
//                }
//            }
//        )
//    }
//
//    PlayerControls(
//        exoPlayer = exoPlayer,
//        totalDuration = { totalDuration },
//        currentTime = { currentTime },
//        bufferPercentage = { bufferedPercentage }
//    )
//}

@Composable
fun VideoPlayer(
    navController: NavHostController,
    url: String,
    isFullScreen: Boolean,
    onPlayerReleased: () -> Unit,
    onFullScreenToggle: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }

    val context = LocalContext.current
    var isPlayerReady by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(true) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_READY -> {
                        isPlayerReady = true
                        isBuffering = false
                    }

                    Player.STATE_BUFFERING -> isBuffering = true
                    Player.STATE_ENDED, Player.STATE_IDLE -> isBuffering = false
                }
            }
        }

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
            onPlayerReleased() // Notify that player is released
        }
    }

    // Handle back press
    BackHandler {
        exoPlayer.release() // Release the player immediately
        onPlayerReleased() // Hide player
        navController.popBackStack() // Navigate back
    }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { showControls = !showControls }
                )
            },
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        }
    )

    if (showControls) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {
                        exoPlayer.seekTo(max(0, exoPlayer.currentPosition - 10000))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Replay10,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        if (isPlaying) {
                            exoPlayer.pause()
                        } else {
                            exoPlayer.play()
                        }
                        isPlaying = !isPlaying
                    }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        exoPlayer.seekTo(
                            min(
                                exoPlayer.duration,
                                exoPlayer.currentPosition + 10000
                            )
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Forward10,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // show total video time
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = "LIVE",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    // full screen toggle button
                    IconButton(
                        onClick = onFullScreenToggle,
                    ) {
                        Icon(
                            imageVector = if (isFullScreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                // seek bar
                var value by remember { mutableFloatStateOf(.5f) }
                var duration by remember { mutableLongStateOf(0L) }
                var currentPosition by remember { mutableLongStateOf(0L) }

                PlaybackSlider(
                    value = value,
                    onValueChange = { value = it },
                    duration = duration,
                    currentPosition = currentPosition
                )
            }
        }
    }
}


@Composable
fun PlaybackSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    duration: Long,
    currentPosition: Long
) {
    Box {
        SliderBackground(
            value = value,
            onValueChange = onValueChange,
            duration = duration,
            currentPosition = currentPosition
        )
        SliderForeground(
            value = value,
            onValueChange = onValueChange,
            duration = duration,
            currentPosition = currentPosition
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderForeground(
    value: Float,
    onValueChange: (Float) -> Unit,
    duration: Long,
    currentPosition: Long
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        thumb = {
            Box(
                Modifier
                    .size(16.dp)
                    .padding(4.dp)
                    .background(Color.Red, CircleShape)
            )
        },
        track = { sliderState ->
            val fraction by remember {
                derivedStateOf {
                    (sliderState.value - sliderState.valueRange.start) /
                            (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }

            Box(Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth(fraction)
                        .height(2.dp)
                        .background(Color.Red)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderBackground(
    value: Float,
    onValueChange: (Float) -> Unit,
    duration: Long,
    currentPosition: Long
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        thumb = {
            Box(
                Modifier
                    .size(16.dp)
            )
        },
        track = {
            Box(Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Gray)
                )
            }
        }
    )
}