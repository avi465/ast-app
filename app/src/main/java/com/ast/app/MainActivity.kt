package com.ast.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ast.app.graphs.RootNavigationGraph
import com.ast.app.ui.theme.AdvancedStudyTutorialsTheme
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : ComponentActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
//      this will enable edge to edge
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        this will install splash screen
//        installSplashScreen()

        Checkout.preload(applicationContext)

        setContent {
            AdvancedStudyTutorialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph()
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