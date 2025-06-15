package com.ast.app.presentation.application.shop.payment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ast.app.data.verifyPayment
import com.ast.app.model.PaymentVerifyResponse
import com.razorpay.Checkout
import com.razorpay.PaymentData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

sealed interface PaymentVerificationUiState {
    data class Success(val order: PaymentVerifyResponse) : PaymentVerificationUiState
    data class Error(val error: String) : PaymentVerificationUiState
    data object Loading : PaymentVerificationUiState
}

class PaymentViewModel : ViewModel() {

    private val _paymentVerificationUiState =
        MutableStateFlow<PaymentVerificationUiState>(PaymentVerificationUiState.Loading)
    val paymentVerificationUiState: StateFlow<PaymentVerificationUiState> =
        _paymentVerificationUiState.asStateFlow()

    // make this function to be called only once in on init()
    // called when only viewmodel is initialised
    // currently its causing multiple recomposition and hence loading
    // multiple instance of payment activity
    // on quick multiple click of button also causing the same
    // either its in a background or foreground multiple recomposition
    // casing multiple re rendering
    // todo: fix this issue

    fun startPayment(activity: Activity, amount: Int, orderId: String, currency: String) {
        val razorpay = Checkout()
        // todo: remove and pass from serverside metadata fetch
        //       in options in production
        razorpay.setKeyID("rzp_test_zkL8eAmaTge0Ak")

        try {
            val options = JSONObject().apply {
                // todo: remove and fetch from server
                // put("key", "rzp_test_zkL8eAmaTge0Ak")
                put("amount", amount) // pass amount in currency subunit, mandatory
                put("currency", currency) // mandatory

                // business name
                put("name", "Advance Study Tutorials")
                put("description", "Test Transaction")
                // put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")

                put("order_id", orderId) // order id, mandatory,

                // prefill details
                // todo: to be fetched from server
                put("prefill.name", "Test User")
                put("prefill.email", "testuser@test.com")
                put("prefill.contact", "+919999999999")
                // put("prefill.method", "upi")

                // put("theme.color", "#585992")
                put("retry.enabled", "false")
            }
            razorpay.open(activity, options)

        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Log.d("PaymentSuccess Test", "In payment success function")
        verifyRazorpayPayment(p1)
        Log.d("Payment", "Payment success: PaymentData: ${p1?.data}")

    }

    fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Log.d("PaymentError Test", "In payment error function")
        Log.d("Payment", "Payment error: ${p2?.data}}")
    }

    private fun verifyRazorpayPayment(p1: PaymentData?) {
        val razorpayPaymentId = p1?.paymentId ?: ""
        val razorpayOrderId = p1?.orderId ?: ""
        val razorpaySignature = p1?.signature ?: ""

        viewModelScope.launch {
            try {
                val response = verifyPayment(razorpayPaymentId, razorpayOrderId, razorpaySignature)
                _paymentVerificationUiState.value = PaymentVerificationUiState.Success(response)
            } catch (e: Exception) {
                _paymentVerificationUiState.value =
                    PaymentVerificationUiState.Error(e.message.toString())

            }
        }
    }
}
