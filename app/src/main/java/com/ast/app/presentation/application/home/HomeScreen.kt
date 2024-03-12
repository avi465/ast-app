package com.ast.app.presentation.application.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.DownloadDone
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.presentation.common.BannerPager

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scrollState = rememberScrollState()

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .verticalScroll(state = scrollState, enabled = true)
        ) {
            BannerPager()
            QuickAccess(modifier = Modifier)
            LiveClassCard()
            Spacer(modifier = Modifier.height(24.dp))
            Recommended(modifier = Modifier)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
fun QuickAccess(modifier: Modifier) {
    val list = listOf(
        Icons.Outlined.Draw,
        Icons.Outlined.Sensors,
        Icons.Outlined.DownloadDone,
        Icons.Outlined.Book
    )

    val title = listOf(
        "Quiz",
        "Live Class",
        "Saved",
        "Material"
    )

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            QuickAccessCard(icon = list[0], title = title[0])
            QuickAccessCard(icon = list[1], title = title[1])
            QuickAccessCard(icon = list[2], title = title[2])
            QuickAccessCard(icon = list[3], title = title[3])
        }
    }
}

@Composable
fun QuickAccessCard(icon: ImageVector, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Row(
                modifier = Modifier
                    .size(64.dp)
                    .padding(20.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun LiveClassCard() {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Live class",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
            )
        }
        Card(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.course_img2),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun Recommended(modifier: Modifier) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Recommended courses",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
            )
        }
        RecommendedPager(modifier = modifier)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendedPager(modifier: Modifier) {
    val imageSlider = listOf(
        painterResource(id = R.drawable.course_img1),
        painterResource(id = R.drawable.course_img1),
    )

    val pagerState = rememberPagerState(pageCount = {
        imageSlider.size
    })

    Box {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(200.dp)
        ) { page ->
            Card(
                Modifier
                    .padding(end = 16.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = imageSlider[page],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

//@Composable
//fun Feedback() {
//    Column {
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Image(
//            imageVector = ImageVector.vectorResource(R.drawable.thanks_feedback),
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            modifier = Modifier.padding(horizontal = 56.dp)
//        )
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxSize()
//        ) {
//            Text(
//                text = "Thanks for trying the app",
//                style = MaterialTheme.typography.titleMedium.copy(
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//            Button(onClick = { /*TODO*/ }) {
//                Text(text = "Feedback")
//            }
//        }
//    }
//}