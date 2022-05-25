package com.shoppi.app.model

import com.google.gson.annotations.SerializedName

data class Layout(
    @SerializedName("layout_id") val layoutId: String,
    val label: String,
    val memo: String
)



