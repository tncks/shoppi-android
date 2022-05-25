package com.shoppi.app.domain

import com.shoppi.app.util.smartTruncate

data class DevByteVideo(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String
) {


    val shortDescription: String
        get() = description.smartTruncate(200)
}
