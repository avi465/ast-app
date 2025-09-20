package com.ast.app.presentation.application.live.videoplayerutils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

@androidx.annotation.OptIn(UnstableApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VideoPlayer(
    modifier: Modifier,
    navController: NavHostController,
    url: String,
    isFullScreen: Boolean,
    onPlayerReleased: () -> Unit,
    onFullScreenToggle: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(true) }
    var showControls by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val activity = context as? Activity
    var isPlayerReady by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var isSeeking by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

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
                        duration = exoPlayer.duration
                        errorMessage = null
                    }

                    Player.STATE_BUFFERING -> isBuffering = true
                    Player.STATE_ENDED, Player.STATE_IDLE -> isBuffering = false
                }
            }

            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying = isPlayingNow
            }

            override fun onPlayerError(error: PlaybackException) {
                isBuffering = false
                isPlaying = false
                errorMessage = when (error.errorCode) {
                    PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> "No internet connection"
                    PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS -> "Something went wrong"
                    PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> "Video not found"
                    PlaybackException.ERROR_CODE_IO_NO_PERMISSION -> "Access denied"
                    PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED -> "Unsupported format"
                    PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> "Decoder error"
                    PlaybackException.ERROR_CODE_DECODING_FAILED -> "Playback failed"
                    PlaybackException.ERROR_CODE_TIMEOUT -> "Connection timed out"
                    PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW -> "Live stream ended"
                    PlaybackException.ERROR_CODE_IO_UNSPECIFIED -> "Stream unavailable"
                    else -> "Playback error"
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

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    // App is in background or screen is off
                    exoPlayer.pause()
                    isPlaying = false
                }

                Lifecycle.Event.ON_RESUME -> {
                    // Optional: resume playing
                    // exoPlayer.play()
                    // isPlaying = true
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
        if (isFullScreen) {
            activity?.window?.insetsController?.apply {
                show(WindowInsets.Type.systemBars()) // Show status and navigation bars
            }
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            onFullScreenToggle()
        } else {
            exoPlayer.release()
            onPlayerReleased()
            navController.popBackStack()
        }
    }

    AndroidView(
        modifier = modifier
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
                resizeMode =
                    AspectRatioFrameLayout.RESIZE_MODE_FIT // Keeps aspect ratio, fits inside container
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    if (errorMessage == null && (showControls || isBuffering || !isPlaying)) {
        LaunchedEffect(isBuffering, isPlaying, showControls) {
            if (isPlaying && !isBuffering) {
                delay(2000L)
                showControls = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
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

                    if (isBuffering) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = Color.White,
                            strokeWidth = 4.dp
                        )
                    } else {
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
                        .padding(horizontal = if (isFullScreen) 36.dp else 16.dp),
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

                Row(
                    modifier = Modifier
                        .padding(horizontal = if (isFullScreen) 36.dp else 0.dp)
                        .padding(bottom = if (isFullScreen) 36.dp else 0.dp)
                ) {

                    PlaybackSlider(
                        isFullScreen = isFullScreen,
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

    if (errorMessage != null) {
        ErrorOverlay(
            message = errorMessage!!,
            onRetry = {
                errorMessage = null
                isBuffering = true
                exoPlayer.setMediaItem(MediaItem.fromUri(url))
                exoPlayer.prepare()
                exoPlayer.play()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaybackSlider(
    isFullScreen: Boolean,
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
                    .background(Color.Gray)
            ) {
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

@Composable
fun ErrorOverlay(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            IconButton(
                onClick = onRetry,
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retry",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


fun formatDuration(duration: Long): String {
    val totalSeconds = duration / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}