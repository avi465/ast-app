package com.ast.app.presentation.application.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ast.app.R

@Composable
fun MyCourseScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        MyCourse()
        MyCourse()
    }
}

@Composable
fun MyCourse() {
    Column {
        ListItem(
            headlineContent = { Text("Three line list item") },
            overlineContent = { Text("OVERLINE") },
            supportingContent = { Text("Secondary text") },
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.course_img1),
                    contentDescription = "placeholder image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(64.dp)
                        .width(64.dp)
                )
            },
            trailingContent = { Text("meta") }
        )
        HorizontalDivider()
    }
}