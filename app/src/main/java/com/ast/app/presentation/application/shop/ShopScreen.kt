package com.ast.app.presentation.application.shop

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ast.app.graphs.CourseDetailsScreen
import com.ast.app.model.Category
import com.ast.app.model.CourseImage
import com.ast.app.network.RESOURCE_ENDPOINT
import com.ast.app.presentation.application.shop.payment.PaymentViewModel
import java.text.NumberFormat
import java.util.Locale

fun numberFormat(number: Int): String? {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return formatter.format(number)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    shopViewModel: ShopViewModel = viewModel(),
    navController: NavController
) {
    val uiState by shopViewModel.shopUiState.collectAsState()
    val isRefreshing by shopViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            shopViewModel.getCourses()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                is ShopUiState.Error -> {
                    val error = (uiState as ShopUiState.Error).error
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = error)
                        TextButton(onClick = { shopViewModel.getCourses() }) {
                            Text(text = "Reload")
                        }
                    }
                }

                ShopUiState.Loading -> {}

                is ShopUiState.Success -> {
                    val courses = (uiState as ShopUiState.Success).courses

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        if (courses != null) {
                            items(courses) { course ->
                                CourseCard(
                                    courseId = course.id,
                                    name = course.name,
                                    description = course.description,
                                    price = course.price,
                                    discount = course.discount,
                                    images = course.images,
                                    category = course.category,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseCard(
    courseId: String,
    name: String,
    description: String,
    price: Int,
    discount: Int,
    images: List<CourseImage>,
    category: Category?,
    navController: NavController,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    Log.d("CourseCard", "CourseCard: $courseId")
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        modifier = Modifier.clickable {
            navController.navigate(CourseDetailsScreen.CourseDetails.route + "/$courseId")
        }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            // Course Thumbnail
            if (images.isNotEmpty()){
                AsyncImage(
                    model = RESOURCE_ENDPOINT + images[0].url + "_portraitSM.webp",
                    contentDescription = images[0].altText,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(112.dp)
                        .height(112.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
            }else{
                Box(
                    modifier = Modifier
                        .width(112.dp)
                        .height(112.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
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
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (category != null) {
                    Text(
                        text = category.name.uppercase(Locale.getDefault()),
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    CoursePricingComponent(price = price, discount = discount)
                }
            }
        }
    }
}

@Composable
fun CoursePricingComponent(price: Int, discount: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "\u20B9${numberFormat(price - (price * discount / 100))}",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
            )
            Text(
                text = price.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    textDecoration = TextDecoration.LineThrough,
                    fontSize = 18.sp
                ),
                modifier = Modifier.alpha(0.6f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LocalOffer,
                    contentDescription = "price decreased",
                    modifier = Modifier.size(20.dp),
                    tint = Color(56, 142, 60)
                )
                Text(
                    text = "${discount}%",
                    modifier = Modifier.padding(0.dp),
                    color = Color(56, 142, 60),
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                )
            }
        }
    }
}
