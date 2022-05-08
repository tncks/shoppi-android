package com.shoppi.app.common

import org.json.JSONObject
import java.util.*

class PrepareJsonHelper {

    fun prepareFlexibleJson(FilePath: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("thumbnail_image_url", FilePath)
        return jsonObject.toString()
    }

    fun prepareFlexibleJson(datas: List<String>): String {
        val jsonObject = JSONObject()
        val profileName = datas[0]
        val location = datas[1]
        val period = datas[2]
        val memo = datas[3]

        jsonObject.put("label", profileName)
        jsonObject.put("location", location)
        jsonObject.put("period", period)
        jsonObject.put("memo", memo)

        return jsonObject.toString()
    }

    fun prepareAccountJson(datas: List<String>, resultParamStringValue: String): String {
        val jsonObject = JSONObject()
        val email = datas[0]
        val password = datas[1]
        val nickname = datas[2]

        jsonObject.put(
            "uid",
            resultParamStringValue
        ) // auto increment value, string parsed from int value, change this later
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("nickname", nickname)
        jsonObject.put("point", "0") // this is fixed value, 0

        return jsonObject.toString()
    }


    // Temporarily Deprecated function
    fun prepareJson(two: String, three: String): String {
        val jsonObject = JSONObject()
        val rand = Random()
        val randValue = rand.nextInt(10000)
        val randValueString = randValue.toString()
        val first = "tplan_$randValueString"

        jsonObject.put("category_id", first)
        jsonObject.put("label", two)
        jsonObject.put("thumbnail_image_url", three)
        jsonObject.put("updated", false)
        jsonObject.put("location", "")
        jsonObject.put("period", "")
        jsonObject.put("memo", "")

        return jsonObject.toString()
    }
    // End of deprecated description

}


// Reference for studying
/*
    fun prepareSmallJson(FilePath: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("thumbnail_image_url", FilePath)
        return jsonObject.toString()
    }


    fun prepareAllJson(datas: List<String>): String {
        val jsonObject = JSONObject()
        val profileName = datas[0]
        val location = datas[1]
        val period = datas[2]
        val memo = datas[3]

        jsonObject.put("label", profileName)
        jsonObject.put("location", location)
        jsonObject.put("period", period)
        jsonObject.put("memo", memo)

        return jsonObject.toString()
    }
    */