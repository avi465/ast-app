package com.ast.app.presentation.application.shop

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ast.app.R
import com.ast.app.graphs.CartScreen
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.presentation.application.shop.payment.PaymentViewModel
import com.ast.app.presentation.common.CircularLoader
import java.text.NumberFormat
import java.util.Locale

fun numberFormat(number: Int): String? {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    return formatter.format(number)
}

@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    shopViewModel: ShopViewModel = viewModel(),
    navController: NavController
) {
    val uiState by shopViewModel.shopUiState.collectAsState()

    when (uiState) {
        is ShopUiState.Error -> {
            val error = (uiState as ShopUiState.Error).error
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = error)
            }
        }

        ShopUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularLoader()
            }
        }

        is ShopUiState.Success -> {
            Log.d("ShopScreen", "Success")
            val courses = (uiState as ShopUiState.Success).courses

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxSize()
            ) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(1),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (courses != null) {
                        items(courses) { course ->
                            CourseCard(
                                name = course.name,
                                description = course.description,
                                price = course.price,
                                discount = course.discount,
                                img = painterResource(id = R.drawable.course_img1),
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseCard(
    name: String,
    description: String,
    price: Int,
    discount: Int,
    img: Painter,
    navController: NavController,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val paymentResult = paymentViewModel.paymentResult.collectAsState().value

    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            painter = img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().height(128.dp)
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent,
            ),
            overlineContent = { Text(text = "BANK/SSC/RAILWAY") },
            headlineContent = { Text(text = name) },
            supportingContent = { Text(text = description) },
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "\u20B9${numberFormat(price - (price * discount / 100))}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "\u20B9${numberFormat(price)}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            textDecoration = TextDecoration.LineThrough,
                            fontStyle = FontStyle.Italic,
                        ),
                        modifier = Modifier.alpha(0.6f)
                    )
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(
                            text = "${discount}% off",
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
                        )
                    }
                }
//                Button(onClick = {
//                    if (context is Activity) {
//                        paymentViewModel.initPayment(
//                            context,
//                            ((price - (price * discount / 100)) * 100).toString()
//                        )
//                    }
//                    navController.navigate(CartScreen.Checkout.route)
//                }) {
//                    Text(text = "Buy Now")
//                }
//
//                paymentResult?.let {
//                    when (it) {
//                        is PaymentViewModel.PaymentResult.Success -> {
//                            Toast.makeText(
//                                context,
//                                "Payment Successful: ${it.paymentId}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                        is PaymentViewModel.PaymentResult.Error -> {
//                            Toast.makeText(
//                                context,
//                                "Payment Failed: ${it.errorMessage}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                }
            }
        }
    }
//}