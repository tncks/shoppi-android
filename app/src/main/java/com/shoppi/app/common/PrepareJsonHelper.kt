package com.shoppi.app.common

import org.json.JSONObject

class PrepareJsonHelper {
    fun prepareSmallJson(FilePath: String): String {
        val jsonObject = JSONObject()

        jsonObject.put("thumbnail_image_url", FilePath)


        return jsonObject.toString()
    }


    fun prepareJson(one: String, two: String, three: String): String {
        val jsonObject = JSONObject()
        val first = "tplan_$one"

        jsonObject.put("category_id", first)
        jsonObject.put("label", two)
        jsonObject.put("thumbnail_image_url", three)
        jsonObject.put("updated", false)

        return jsonObject.toString()
    }
}