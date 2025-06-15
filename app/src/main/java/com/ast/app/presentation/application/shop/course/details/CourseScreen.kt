package com.ast.app.presentation.application.shop.course.details

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ast.app.network.IMAGE_RESOURCE_ENDPOINT
import com.ast.app.presentation.application.shop.numberFormat
import com.ast.app.presentation.application.shop.payment.PaymentVerificationUiState
import com.ast.app.presentation.application.shop.payment.PaymentViewModel
import com.ast.app.presentation.common.CircularLoader
import kotlin.math.floor

@Composable
fun CourseScreen(
    courseId: String,
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = viewModel(),
    courseDetailViewModel: CourseDetailViewModel = viewModel(
        factory = CourseDetailsViewModelProviderFactory(
            courseId = courseId
        )
    ),
    createOrderViewModel: CreateOrderViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by courseDetailViewModel.courseDetailUiState.collectAsState()
    val createOrderUiState by createOrderViewModel.createOrderUiState.collectAsState()
    val paymentVerificationUiState by paymentViewModel.paymentVerificationUiState.collectAsState()

    val isPaymentInProgress = remember { mutableStateOf(false) }

    LaunchedEffect(createOrderUiState) {
        if (createOrderUiState is CreateOrderUiState.Success && isPaymentInProgress.value) {
            val orderResponse = (createOrderUiState as CreateOrderUiState.Success).order
            val amount = orderResponse.amount
            val orderId = orderResponse.id
            val currency = orderResponse.currency

            if (context is Activity) {
                paymentViewModel.startPayment(
                    activity = context,
                    amount = amount,
                    currency = currency,
                    orderId = orderId
                )
                //todo: implement .then mechanism such that after payment intent
                // is done then only it will set payment in progress to false
                // currently it works as when intent start it makes button visible
                // which may cause multiple clicks, prevent this using after payment intent redirect
                // back to the course screen
                isPaymentInProgress.value = false  // Mark payment in progress false
            }
        }
    }

    // todo: not working
    LaunchedEffect(paymentVerificationUiState) {
        if (paymentVerificationUiState is PaymentVerificationUiState.Success) {
            Toast.makeText(
                context,
                (paymentVerificationUiState as PaymentVerificationUiState.Success).toString(),
                Toast.LENGTH_SHORT
            ).show()
            isPaymentInProgress.value = false
        } else if (paymentVerificationUiState is PaymentVerificationUiState.Error) {
            Toast.makeText(
                context,
                (paymentVerificationUiState as PaymentVerificationUiState.Error).error,
                Toast.LENGTH_SHORT
            ).show()
            isPaymentInProgress.value = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is CourseDetailUiState.Success -> {
                val course = (uiState as CourseDetailUiState.Success).course
                if (course != null) {
                    Scaffold(
                        bottomBar = {
                            Surface(
                                onClick = { /*TODO*/ },
                                color = NavigationBarDefaults.containerColor
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        Text(
                                            text = "\u20B9${numberFormat(course.price - (course.price * course.discount / 100))}",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontSize = 18.sp
                                            )
                                        )
                                        Text(
                                            text = course.price.toString(),
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
                                                text = "${course.discount}%",
                                                modifier = Modifier.padding(0.dp),
                                                color = Color(56, 142, 60),
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontSize = 18.sp
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Button(
                                        onClick = {
                                            if (!isPaymentInProgress.value) {
                                                isPaymentInProgress.value = true
                                                createOrderViewModel.createRazorpayOrder(course.id)
                                            }
                                        },
                                        enabled = !isPaymentInProgress.value
                                    ) {
                                        Text(text = "Buy Now")
                                    }
                                    when (createOrderUiState) {
                                        is CreateOrderUiState.Error -> {
                                            Toast.makeText(
                                                context,
                                                (createOrderUiState as CreateOrderUiState.Error).error,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            isPaymentInProgress.value = false
                                        }

                                        CreateOrderUiState.Loading -> {
                                            // CircularLoader()
                                        }

                                        else -> {}
                                    }
                                }

                            }
                        }
                    ) { padding ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {
                                AsyncImage(
                                    model = IMAGE_RESOURCE_ENDPOINT + course.images[0].url + "_landscapeSM.webp",
                                    contentDescription = course.images[0].altText,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(196.dp)
                                )
                            }
                            item {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    // Course Title
                                    Text(
                                        text = course.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    // Short Description
                                    Text(
                                        text = course.description,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Instructor Name
                                    Text(
                                        text = "By AST Instructor", // Ideally from course.instructor.name
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    // Category Label
                                    CategoryLabel(
                                        text = course.category.name
                                    )
                                }
                            }

                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(
                                                alpha = 0.2f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "About the Course",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    Text(
                                        text = course.details,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                        lineHeight = 20.sp
                                    )
                                }
                            }

                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(
                                                alpha = 0.2f
                                            ),
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Course Information",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )

                                    InfoRow(label = "Start Date", value = "01/01/2024")
                                    InfoRow(label = "Status", value = "Ongoing")
                                    InfoRow(label = "Outcome", value = "Masterclass")
                                }
                            }

                            item {
//                                Column(
//                                    modifier = Modifier
//                                        .padding(horizontal = 16.dp, vertical = 8.dp)
//                                        .background(
//                                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
//                                            shape = RoundedCornerShape(16.dp)
//                                        )
//                                        .padding(16.dp)
//                                ) {
//                                    Text(
//                                        text = "Ratings",
//                                        style = MaterialTheme.typography.titleMedium,
//                                        fontWeight = FontWeight.SemiBold,
//                                        modifier = Modifier.padding(bottom = 8.dp)
//                                    )
//
//                                    RatingBar(rating = 4.5f)
//                                }

                                RatingsAndReviewsSection(
                                    averageRating = 4.5f,
                                    totalReviews = 128,
                                    reviews = listOf(
                                        "This course gave me a solid foundation in Android development.",
                                        "Very well explained content. The projects helped me a lot!"
                                    )
                                )

                            }


//                        item {
//                            Text("Syllabus", style = MaterialTheme.typography.titleMedium)
//                            course.modules.forEach { module ->
//                                Text("• ${module.title}", fontWeight = FontWeight.SemiBold)
//                                module.contentList.forEach { content ->
//                                    Text("  - $content", style = MaterialTheme.typography.bodySmall)
//                                }
//                            }
//                        }

                            // imp: this is to prevent hiding of content at very bottom behind bottom bar
                            item {
                                Column(modifier = Modifier.padding(padding)) {
                                }
                            }
                        }
                    }

                }
            }

            is CourseDetailUiState.Error -> {
                val error = (uiState as CourseDetailUiState.Error).error
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = error)
                }
            }

            CourseDetailUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularLoader()
                }
            }
        }
        Text(
            text = "Id: $courseId",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    val fullStars = floor(rating).toInt()
    val hasHalfStar = (rating % 1) >= 0.25 && (rating % 1) <= 0.75
    val emptyStars = maxRating - fullStars - if (hasHalfStar) 1 else 0

    Row(modifier = modifier) {
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 2.dp)
            )
        }

        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 2.dp)
            )
        }

        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                modifier = Modifier.padding(end = 2.dp)
            )
        }

        Text(
            text = "$rating",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun CategoryLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun RatingsAndReviewsSection(
    averageRating: Float,
    totalReviews: Int,
    reviews: List<String>
) {
    var visibleReviews by remember { mutableStateOf(2) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text("Ratings & Reviews", style = MaterialTheme.typography.titleMedium)

        // Rating Summary
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = String.format("%.1f", averageRating),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))
            RatingBar(rating = averageRating)
            Spacer(Modifier.width(8.dp))
            Text("($totalReviews reviews)", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(12.dp))

        reviews.take(visibleReviews).forEach { review ->
            Text(
                text = review,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        if (visibleReviews < reviews.size) {
            TextButton(onClick = { visibleReviews += 2 }) {
                Text("Load More")
            }
        } else if (reviews.size > 2) {
            TextButton(onClick = { visibleReviews = 2 }) {
                Text("Show Less")
            }
        }
    }
}
