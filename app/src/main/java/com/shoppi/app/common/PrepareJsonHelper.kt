package com.shoppi.app.common

import org.json.JSONObject

class PrepareJsonHelper {
    fun prepareSmallJson(FilePath: String): String {
        val jsonObject = JSONObject()

        jsonObject.put("thumbnail_image_url", FilePath)


        return jsonObject.toString()
    }
}