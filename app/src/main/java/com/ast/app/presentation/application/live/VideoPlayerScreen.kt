package com.ast.app.presentation.application.live

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ast.app.presentation.component.VideoPlayer

@Composable
fun VideoPlayerScreen(
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.padding(it)
    ) {
//        val url = "http://137.184.36.54:8000/live/obs_stream/index.m3u8"
        val url =
            "https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8"
        VideoPlayer(url = url)

//        VideoPlayerScreenDetailsCard()
//        VideoPlayerScreenChatField()
//        Spacer(modifier = Modifier.weight(1f))
//        VideoPlayerScreenChat()
    }
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
                style = MaterialTheme.typography.bodySmall
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
