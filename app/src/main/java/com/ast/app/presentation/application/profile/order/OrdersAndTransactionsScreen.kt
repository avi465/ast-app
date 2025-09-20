package com.ast.app.presentation.application.profile.order

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
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ast.app.model.PaymentModel
import com.ast.app.network.RESOURCE_ENDPOINT
import com.ast.app.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersAndTransactionsScreen(
    navController: NavHostController,
    ordersAndTransactionsViewModel: OrdersAndTransactionsViewModel = viewModel(),
) {
    val ordersAndTransactionsState by ordersAndTransactionsViewModel.ordersAndTransactionsState.collectAsState()
    val isRefreshing by ordersAndTransactionsViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            ordersAndTransactionsViewModel.fetchTransactionsForUser()
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (ordersAndTransactionsState) {
                is UiState.Success -> {
                    val payments =
                        (ordersAndTransactionsState as UiState.Success<List<PaymentModel>>).data
                    if (payments.isNotEmpty()) {
                        LazyColumn(
                            contentPadding = PaddingValues( 8.dp)
                        ) {
                            items(payments) { payment ->
                                OrderPaymentItem(payment = payment)
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "No transactions found",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }

                is UiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = (ordersAndTransactionsState as UiState.Error).error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        TextButton(onClick = {
                            ordersAndTransactionsViewModel.fetchTransactionsForUser()
                        }) {
                            Text(text = "Reload")
                        }
                    }
                }

                is UiState.Loading -> {}
            }
        }
    }
}

@Composable
fun OrderPaymentItem(payment: PaymentModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle onClick */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = RESOURCE_ENDPOINT + payment.order.course.images.firstOrNull()?.url + "_landscapeSM.webp",
                contentDescription = payment.order.course.images.firstOrNull()?.altText ?: "Course Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = payment.order.course.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Text(
                    text = payment.order.course.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Payment ID: ${payment.razorpayPaymentId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = "Date",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = payment.createdAt.take(10),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}