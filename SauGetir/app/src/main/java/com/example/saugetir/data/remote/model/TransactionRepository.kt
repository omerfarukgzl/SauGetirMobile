package com.example.saugetir.data.remote.model

import com.example.saugetir.data.remote.model.request.EncryptedTokenRequest
import com.example.saugetir.data.remote.model.request.TokenRequest
import com.example.saugetir.data.remote.model.response.TokenResponse
import retrofit2.Call

class TransactionRepository(private var transactionApi: TransactionApi)  {

    fun requestInitToken(request: EncryptedTokenRequest): Call<TokenResponse> {
       return transactionApi.getToken(request)
    }
}

/*class MerchantRepository(private var merchantApi: MerchantApi) {

    fun requestInitToken(request: InitRequest): Call<InitResponse> {
       return merchantApi.requestInitToken(request)
    }
}*/