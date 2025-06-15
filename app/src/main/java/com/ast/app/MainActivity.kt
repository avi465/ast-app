package com.ast.app

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.ast.app.network.utils.CookieManager
import com.ast.app.network.utils.NetworkConnectivityObserver
import com.ast.app.network.utils.NetworkStatus
import com.ast.app.presentation.application.shop.payment.PaymentViewModel
import com.ast.app.ui.theme.AdvancedStudyTutorialsTheme
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {
    // network connectivity observer
    private lateinit var connectivityObserver: ConnectivityObserver

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        // enable edge to edge
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        // Persistent session manager initialization
        val prefs = getSharedPreferences("cookie_prefs", MODE_PRIVATE)
        CookieManager.init(prefs)

        // Lock the entire app UI to portrait mode.
        // Orientation can still be dynamically controlled within the app as needed.
        // For a permanent orientation lock, you can set it in the manifest file.
        // Note: Locking orientation should be done cautiously and only when necessary.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // install splash screen
        // installSplashScreen()

        // network connectivity observer
        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        // razorpay checkout preload at the time of activity creation
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

                    // rendering the ui according to the network connectivity
                    // todo: when we turn off data first and launch the app it not working as expected
                    if (status == NetworkStatus.Available) {
                        RootNavigationGraph()
                    } else {
                        // render no connection ui
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

    // payment view model for delegating the payment result to it
    private val paymentViewModel: PaymentViewModel by viewModels()

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        // Delegate the success result to ViewModel
        paymentViewModel.onPaymentSuccess(p0, p1)
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        // Delegate the error result to ViewModel
        paymentViewModel.onPaymentError(p0, p1, p2)
    }

    companion object {
        const val SHARED_PREFS = "com.ast.app.SHARED_PREFS"
        const val CART_PREFS = "com.ast.app.CART_PREFS"
    }
}