package com.ast.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ast.app.graphs.RootNavigationGraph
import com.ast.app.network.utils.ConnectivityObserver
import com.ast.app.network.utils.NetworkConnectivityObserver
import com.ast.app.network.utils.NetworkStatus
import com.ast.app.ui.theme.AdvancedStudyTutorialsTheme
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {
    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        // This will enable edge to edge
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        // This will install splash screen
        // installSplashScreen()

        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        Checkout.preload(applicationContext)

        setContent {
            AdvancedStudyTutorialsTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val status by connectivityObserver.observe().collectAsState(
                        initial = NetworkStatus.Available
                    )

                    if (status == NetworkStatus.Available) {
                        RootNavigationGraph()
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Network status: $status")
                        }
                    }
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, "Thanks for purchasing", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SHARED_PREFS = "com.ast.app.SHARED_PREFS"
        const val CART_PREFS = "com.ast.app.CART_PREFS"
    }
}