package com.example.saugetir.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.saugetir.data.remote.model.RetrofitClient
import com.example.saugetir.data.remote.model.TransactionRepository
import com.example.saugetir.data.remote.model.errorResponse.ErrorResponse
import com.example.saugetir.data.remote.model.request.EncryptedTokenRequest
import com.example.saugetir.data.remote.model.response.TokenResponse
import com.example.saugetir.databinding.ActivityMainBinding
import com.example.saugetir.utils.Constants.MERCHANT_CODE
import com.example.saugetir.utils.EncryptionUtil
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.camasirSuyuEkle.setOnClickListener {
            val newSayi= binding.camasirSayi.text.toString().toInt() + 1
            binding.camasirSayi.setText((newSayi).toString())
            calculateAmount(newSayi.toString(),binding.suSayi.text.toString(),binding.sutSayi.text.toString())
        }

        binding.suEkle.setOnClickListener {
            val newSayi= binding.suSayi.text.toString().toInt() + 1
            binding.suSayi.setText((newSayi).toString())
            calculateAmount(binding.camasirSayi.text.toString(),newSayi.toString(),binding.sutSayi.text.toString())
        }

        binding.sutEkle.setOnClickListener {
            val newSayi= binding.sutSayi.text.toString().toInt() + 1
            binding.sutSayi.setText((newSayi).toString())
            calculateAmount(binding.camasirSayi.text.toString(),binding.suSayi.text.toString(),newSayi.toString())
        }

        binding.saupayOde.setOnClickListener {

            saupay_pay(binding.amountText.text.toString().toBigDecimal())
        }


    }

    fun saupay_pay(amount : BigDecimal)
    {
        try {
            var encryptedTokenRequest = EncryptedTokenRequest()

            val paramObject = JSONObject()
            paramObject.put("amount", amount)
            paramObject.put("merchantCode", MERCHANT_CODE)

            encryptedTokenRequest.data = EncryptionUtil.encrypt(paramObject.toString())


            val repository = TransactionRepository(RetrofitClient.getToken())
            val call = repository.requestInitToken(encryptedTokenRequest)

            call.enqueue(object : Callback<TokenResponse> {
                //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    Log.d("MainActivity", response.body().toString())
                    if (response.isSuccessful) {

                        Log.d("MainActivity", response.body().toString())
                        Log.d("MainActivity", "istek geldi ve başarılı !!!")
                        if (response.body()!= null){
                            Log.d("MainActivity", "isSuccessful")
                            val tokenResponse = response.body()
                            Log.d("MainActivity", "Payment Token Response: -->" +tokenResponse!!.token.toString())
                            val deepLinkUrl = "http://www.saupay5454.com/payment?token=" + tokenResponse!!.token.toString();
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deepLinkUrl))
                            startActivity(intent)
                        }

                    }
                    else {
                        val errorBody = response.errorBody()?.string()

                        val gson = Gson()
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                        val transactionResponse = errorResponse.status?.errorDescription
                        // Burada, errorResponse özel hata cevabını içerecektir.

                        Log.d("MainActivity",errorBody.toString())
                        Log.d("MainActivity", "isn't Successful")
                        Log.d("MainActivity", transactionResponse.toString())
                        // binding.errorMaessage.text = transactionResponse.toString()
                    }

                }
                // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("MainActivityError", t.toString()!!)
                }
            })

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    fun calculateAmount(camasirSayi: String, suSayi: String, sutSayi: String): String {
        var camasirSayi = camasirSayi.toInt()
        var suSayi = suSayi.toInt()
        var sutSayi = sutSayi.toInt()
        var amount = ((camasirSayi *  (binding.camasirAmount.text.toString().split(".")[0].toInt())) +
                (suSayi * (binding.suAmount.text.toString().split(".")[0].toInt()))+
                (sutSayi * (binding.sutAmount.text.toString().split(".")[0].toInt())))

        binding.araToplam.text = amount.toString() + ".00" + " TL"
        binding.amountText.text = (amount.toString().toInt() - 5).toString() + ".00"
        return amount.toString()
    }



    /*fun payment(userId: String)
    {        val requestInitToken = TokenRequest(
        "100.00",
        MERCHANT_CODE,
    )
        val merchantApi = RetrofitClient.getSauPayApi()
        val repository = TransactionRepository(merchantApi)

        val call = repository.requestInitToken(requestInitToken)

        call.enqueue(object : Callback<TokenResponse> {
            //onResponse fonksiyonu, sunucudan dönen cevabı işlemek için kullanılır.
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                Log.d("MainActivity", response.body().toString())
                if (response.isSuccessful) {

                    Log.d("MainActivity", response.body().toString())
                    Log.d("MainActivity", "istek geldi ve başarılı !!!")
                    if (response.body()!!.token != null){
                        Log.d("MainActivity", "isSuccessful")
                        val tokenResponse = response.body()!!.token
                    }

                }
                else {
                    val errorBody = response.errorBody()?.string()

                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)

                    val transactionResponse = errorResponse.status?.errorDescription
                    // Burada, errorResponse özel hata cevabını içerecektir.

                    Log.d("MainActivity",errorBody.toString())
                    Log.d("MainActivity", "isn't Successful")
                    Log.d("MainActivity", transactionResponse.toString())
                    // binding.errorMaessage.text = transactionResponse.toString()
                }

            }
            // onFailure fonksiyonu, sunucuya istek gönderirken bir hata oluşması durumunda çalışır.
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.e("MainActivityError", t.message!!)
            }
        })





    }*/

}





/*        val client = OkHttpClient()

// Create a PaymentRequest object with the merchant code and amount
        val request = PaymentRequest("merchant123", (100.0).toBigDecimal())

// Convert the PaymentRequest object to JSON
        val gson = Gson()
        val json = gson.toJson(request)

// Create a new request object
        val mediaType = MediaType.parse("application/json")
        val requestBody = RequestBody.create(mediaType, json)

        val httpRequest = Request.Builder()
            .url("http://10.0.2.2/8080/payment2")
            .post(requestBody)
            .build()

// Send the request and get the response
        val response = client.newCall(httpRequest).execute()
        val token = response.body()?.string()


        binding.button.setOnClickListener {
            binding.textView.text = token
        }*/