package com.ast.app.presentation.application.shop.payment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class PaymentViewModel : ViewModel(){

    private val _paymentResult = MutableStateFlow<PaymentResult?>(null)
    val paymentResult: StateFlow<PaymentResult?> = _paymentResult

    fun startPayment(activity: Activity, amount: String) {
        val razorpay: Checkout = Checkout()
        razorpay.setKeyID("rzp_test_zkL8eAmaTge0Ak")

        try {
            val options = JSONObject().apply {
                put("name", "AST")
                put("theme.color", "#000")
                put("currency", "INR")
                put("amount", amount) // pass amount in currency subunit
            }
            razorpay.open(activity, options)
            Log.d("Payment ViewModel", "Payment initiated")
        } catch (e: Exception) {
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

//    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        Log.d("PaymentSuccess Test", "In payment success function")
//        viewModelScope.launch {
//            _paymentResult.emit(PaymentResult.Success(p1?.paymentId))
//            Log.d("Payment", "Payment success: Payment ID: ${p1?.paymentId}")
//        }
//    }
//
//    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        Log.d("PaymentError Test", "In payment error function")
//        viewModelScope.launch {
//            _paymentResult.emit(PaymentResult.Error(p1 ?: "Unknown error"))
//            Log.d("Payment", "Payment error: Code: $p0, Message: ${p1 ?: "Unknown error"}")
//        }
//    }

    sealed class PaymentResult {
        data class Success(val paymentId: String?) : PaymentResult()
        data class Error(val errorMessage: String?) : PaymentResult()
    }
}
