package com.example.saugetir.data.remote.model.request

import java.math.BigDecimal


data class TokenRequest(
    val amount: BigDecimal,
    val merchantCode: String
) {

}