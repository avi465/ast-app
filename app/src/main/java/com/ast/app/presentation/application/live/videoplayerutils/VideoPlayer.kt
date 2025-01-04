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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

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

    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var isSeeking by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_READY -> {
                        isPlayerReady = true
                        isBuffering = false
                        duration = exoPlayer.duration
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
            onPlayerReleased()
        }
    }

    // Update the current position during playback if not seeking
    LaunchedEffect(Unit) {
        while (true) {
            if (!isSeeking) {
                currentPosition = exoPlayer.currentPosition
            }
            delay(100) // Update every 100ms for smooth slider progress
        }
    }

    BackHandler {
        exoPlayer.release()
        onPlayerReleased()
        navController.popBackStack()
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
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${formatDuration(currentPosition)} / ${formatDuration(duration)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

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

                PlaybackSlider(
                    value = if (duration > 0) currentPosition.toFloat() / duration else 0f,
                    onValueChange = { value ->
                        isSeeking = true
                        currentPosition = (value * duration).toLong()
                    },
                    onSeek = { value ->
                        isSeeking = false
                        exoPlayer.seekTo((value * duration).toLong())
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaybackSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onSeek: (Float) -> Unit
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        onValueChangeFinished = {
            onSeek(value) // Trigger seek action when the user finishes interaction
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
        thumb = {
            Box(
                Modifier
                    .size(0.dp) // Match the track height
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

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)) {
                Box(
                    Modifier
                        .fillMaxWidth(fraction)
                        .height(4.dp) // Match the track height
                        .background(Color.Red)
                )
            }
        }
    )
}

fun formatDuration(duration: Long): String {
    val totalSeconds = duration / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}