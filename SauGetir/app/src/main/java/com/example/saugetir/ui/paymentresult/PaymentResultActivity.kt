package com.example.saugetir.ui.paymentresult

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.saugetir.R

class PaymentResultActivity : AppCompatActivity() {

    private var result: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_result)

        val intent = intent
        val data: Uri? = intent.data

        if (data != null) {
            val receivedData = data.getQueryParameter("result")
            Log.d("SauPaydenGelenSonuc: " , receivedData.toString())
            result = receivedData?.toBoolean()
        }


    }

    fun getResult(): Boolean? {
        return result
    }
}