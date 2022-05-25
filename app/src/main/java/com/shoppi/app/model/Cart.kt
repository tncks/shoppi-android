package com.shoppi.app.model

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart_id") val cartId: String,
    val label: String,
    val memo: String
)



