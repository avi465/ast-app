package com.ast.app.presentation.component

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageButton
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ast.app.R

//@OptIn(UnstableApi::class) @Composable
//fun VideoPlayer(url: String){
//    var lifecycle by remember {
//        mutableStateOf(Lifecycle.Event.ON_CREATE)
//    }
//    val context = LocalContext.current
//
//    val mediaItem =
//        MediaItem.fromUri(
//            url
//        )
//
////      progressive video:
////        val mediaSource: MediaSource =
////            ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
////                .createMediaSource(mediaItem)
//
////        non progressive video:
//    val mediaSource: MediaSource =
//        HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
//            .createMediaSource(mediaItem)
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaSource(mediaSource)
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(key1 = lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            lifecycle = event
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            exoPlayer.release()
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
//
//    AndroidView(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .aspectRatio(16f / 9f),
//        factory = {
//            PlayerView(context).also { playerView ->
//                playerView.player = exoPlayer
//            }
//        },
//        update = {
//            when (lifecycle) {
//                Lifecycle.Event.ON_RESUME -> {
//                    it.onPause()
//                    it.player?.pause()
//                }
//
//                Lifecycle.Event.ON_PAUSE -> {
//                    it.onResume()
//                }
//
//                else -> Unit
//            }
//        }
//    )
//}

// local: "android.resource://${context.packageName}/${R.raw.sample_video}"

// online:
//       "https://file-examples.com/storage/fe793dd9be65a9b389251ea/2017/04/file_example_MP4_480_1_5MG.mp4"

// live playback:
//        "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8"



//@Composable
//fun VideoPlayer(navController: NavController, url: String, onPlayerReleased: () -> Unit) {
//    val context = LocalContext.current
//
//    // Create and manage ExoPlayer instance
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri(url))
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    // Cleanup resources when Composable is removed
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//            onPlayerReleased() // Notify that player is released
//        }
//    }
//
//    // Handle back press
//    BackHandler {
//        exoPlayer.release() // Release the player immediately
//        onPlayerReleased() // Hide player
//        navController.popBackStack() // Navigate back
//    }
//
//    // Render the PlayerView
//    AndroidView(
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//            }
//        },
//        modifier = Modifier.fillMaxSize()
//    )
//}


//@RequiresApi(Build.VERSION_CODES.R)
//@Composable
//fun VideoPlayer(
//    navController: NavController,
//    url: String,
//    onPlayerReleased: () -> Unit
//) {
//    val context = LocalContext.current
//    val activity = context as? Activity
//    var isFullscreen by rememberSaveable { mutableStateOf(false) } // Fullscreen state
//
//    // Create and manage ExoPlayer instance
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri(url))
//            prepare()
//            playWhenReady = true
//        }
//    }
//
//    // Cleanup resources when Composable is removed
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//            onPlayerReleased() // Notify that player is released
//        }
//    }
//
//    // Handle fullscreen toggling
//    BackHandler {
//        if (isFullscreen) {
//            exitFullscreen(activity)
//            isFullscreen = false
//        } else {
//            exoPlayer.release() // Release the player
//            onPlayerReleased() // Hide player
//            navController.popBackStack() // Navigate back
//        }
//    }
//
//    // Render the PlayerView
//    AndroidView(
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//                useController = false // Enable controls
//                setFullscreenButtonClickListener {
//                    if (!isFullscreen) {
//                        enterFullscreen(activity)
//                        isFullscreen = !isFullscreen
//                    } else {
//                        exitFullscreen(activity)
//                        isFullscreen = !isFullscreen
//                    }
//                }
//            }
//        },
//        modifier = if (isFullscreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()
//    )
//}
//
//@RequiresApi(Build.VERSION_CODES.R)
//private fun enterFullscreen(activity: Activity?) {
//    activity?.window?.insetsController?.apply {
//        hide(WindowInsets.Type.systemBars()) // Hide status and navigation bars
//        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//    }
//    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//}
//
//@RequiresApi(Build.VERSION_CODES.R)
//private fun exitFullscreen(activity: Activity?) {
//    activity?.window?.insetsController?.apply {
//        show(WindowInsets.Type.systemBars()) // Show status and navigation bars
//    }
//    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
//}


@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VideoPlayer(
    url: String,
    thumbnailUrl: String,
    onPlayerReleased: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var isFullscreen by rememberSaveable { mutableStateOf(false) }
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
            onPlayerReleased()
        }
    }

    val swipeGestureModifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, _, _ ->
                if (pan.y > 0) {
                    // Swipe Down: exit fullscreen
                    if (isFullscreen) {
                        exitFullscreen(activity)
                        isFullscreen = false
                    }
                } else if (pan.y < 0) {
                    // Swipe Up: enter fullscreen
                    if (!isFullscreen) {
                        enterFullscreen(activity)
                        isFullscreen = true
                    }
                }
            }
        }

    Box(
        modifier = if (isFullscreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth(),
    ) {
        if (!isPlayerReady) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "Thumbnail",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }

        if (isBuffering) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true // Hide default controls

                    // Custom Fullscreen Button
                    val fullscreenButton = AppCompatImageButton(context).apply {
                        setImageResource(R.drawable.ic_google) // Use your vector drawable or image here
                        setOnClickListener {
                            if (isFullscreen) {
                                exitFullscreen(activity)
                            } else {
                                enterFullscreen(activity)
                            }
                            isFullscreen = !isFullscreen
                        }
                    }
                    addView(fullscreenButton, FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.END or Gravity.BOTTOM
                        setMargins(16, 16, 16, 16)
                    })
                }
            },
            modifier = Modifier
                .then(swipeGestureModifier)
                .fillMaxWidth()
                .alpha(if (isPlayerReady) 1f else 0f) // Hide player until ready
        )
    }

    // Handling full screen transitions
    BackHandler {
        if (isFullscreen) {
            exitFullscreen(activity)
            isFullscreen = false
        } else {
            exoPlayer.release()
            onPlayerReleased()
            (context as? Activity)?.onBackPressed()
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
