package com.ast.app.presentation.application.shop.cart

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
import com.ast.app.presentation.application.shop.numberFormat
import com.ast.app.presentation.application.shop.payment.PaymentViewModel

@Composable
fun CartScreen(navController: NavController, paymentViewModel: PaymentViewModel = viewModel()) {
    val context = LocalContext.current
    val paymentResult = paymentViewModel.paymentResult.collectAsState().value

//    TODO: For testing purpose
    val price = 10000
    val discount = 10

    Scaffold(
        bottomBar = {
            Button(
                onClick = {
                    if (context is Activity) {
                        paymentViewModel.startPayment(
                            context,
                            ((price - (price * discount / 100)) * 100).toString()
                        )
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.button_height)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Proceed to Checkout")
                paymentResult?.let {
                    when (it) {
                        is PaymentViewModel.PaymentResult.Success -> {
                            Toast.makeText(
                                context,
                                "Thanks for purchasing this course! Payment ID: ${it.paymentId}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is PaymentViewModel.PaymentResult.Error -> {
                            Toast.makeText(
                                context,
                                "Payment Failed: ${it.errorMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CartScreenCard()
                CartScreenCard()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "YOU SAVE \u20B9${numberFormat((price * discount / 100))}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                TotalAmount()
            }

        }
    }
}

@Composable
fun CartScreenCard() {
    val price = 10000
    val discount = 10

    Column {
        ListItem(
            overlineContent = { Text("GATE") },
            headlineContent = {
                Text(
                    "General Science",
                )
            },
            supportingContent = {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "\u20B9${numberFormat(price - (price * discount / 100))}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                    )
                    Text(
                        text = "\u20B9${numberFormat(price)}",
                        style = LocalTextStyle.current.copy(
                            textDecoration = TextDecoration.LineThrough,
                            fontStyle = FontStyle.Italic
                        )
                    )
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(
                            text = "${discount}% off",
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
                        )
                    }
                }
            },
            leadingContent = {
                Image(
                    painter = painterResource(id = R.drawable.course_img1),
                    contentDescription = "placeholder image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(96.dp)
                        .width(96.dp)
                )
            },
        )
        HorizontalDivider()
    }
}

@Composable
fun TotalAmount() {
    HorizontalDivider()
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Total",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        )
        Text(
            text = "₹9000",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
    HorizontalDivider()

}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun CartScreenPreview() {
    CartScreen(navController = rememberNavController())
}