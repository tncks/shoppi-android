package com.shoppi.app.ui.tutorials.videotuto

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
