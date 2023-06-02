package com.example.saugetir.data.remote.model

import com.example.saugetir.data.remote.model.request.EncryptedTokenRequest
import com.example.saugetir.data.remote.model.request.TokenRequest
import com.example.saugetir.data.remote.model.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionApi {

    @POST("payment")
    fun getToken (@Body request: EncryptedTokenRequest): retrofit2.Call<TokenResponse>

}