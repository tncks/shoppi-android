package com.shoppi.app.common.jsoncraft

import com.shoppi.app.common.*
import org.json.JSONArray
import org.json.JSONObject
import java.security.SecureRandom

class PrepareJsonHelper {

    fun prepareCategoryPropertyBoolToggleJson(b: Boolean): String {
        val jsonObject = JSONObject()
        jsonObject.put("updated", b)
        return jsonObject.toString()
    }

    fun prepareCleanRemoveIndexingJson(saIndexStr: String, updatedBoolProperty: Boolean = false): String {
        return if (!updatedBoolProperty) {
            inCategoryPlanLogicFalseUpdatedPropertyDoThis(saIndexStr)
        } else {
            inHistoryLogicTrueUpdatedPropertyDoThis(saIndexStr)
        }
    }

    /** 나중에 아래 로직함수 분기 달라져야하는부분 보고 수정 Int.get 하는 확장함수 유틸도 분기별로 함수 두개인데 잘 보고 수정 */

    private fun inCategoryPlanLogicFalseUpdatedPropertyDoThis(saIndexStr: String): String {

        var saIndexOriginalValue = 0
        var indexHelper: Int = -1
        for (n in 0 until 20) {
            ++indexHelper
            if (indexHelper.toString() == saIndexStr) {
                saIndexOriginalValue = indexHelper
                break
            }
        }
        val saIndexRealLiveIndexOnTotalArrayPosValue = saIndexOriginalValue.getSomeOriginInfo() // 달라져야하는부분
        val sameWithSaIndexRealLiveIndexOnTotalArrayPosValueStr = saIndexRealLiveIndexOnTotalArrayPosValue.toString()
        var indexCount = 0
        val consKeyFixes = listOf("uid", "idx")
        val jsonObject = JSONObject()
        jsonObject.put(consKeyFixes[indexCount++], SAFEUID)
        val ja = JSONArray()
        ja.put(sameWithSaIndexRealLiveIndexOnTotalArrayPosValueStr)
        jsonObject.put(consKeyFixes[indexCount], ja)
        return jsonObject.toString()

    }

    private fun inHistoryLogicTrueUpdatedPropertyDoThis(saIndexStr: String): String {

        var saIndexOriginalValue = 0
        var indexHelper: Int = -1
        for (n in 0 until 20) {
            ++indexHelper
            if (indexHelper.toString() == saIndexStr) {
                saIndexOriginalValue = indexHelper
                break
            }
        }
        val saIndexRealLiveIndexOnTotalArrayPosValue = saIndexOriginalValue.getSomeOriginInfo() // 달라져야하는부분
        val sameWithSaIndexRealLiveIndexOnTotalArrayPosValueStr = saIndexRealLiveIndexOnTotalArrayPosValue.toString()
        var indexCount = 0
        val consKeyFixes = listOf("uid", "idx")
        val jsonObject = JSONObject()
        jsonObject.put(consKeyFixes[indexCount++], SAFEUID)
        val ja = JSONArray()
        ja.put(sameWithSaIndexRealLiveIndexOnTotalArrayPosValueStr)
        jsonObject.put(consKeyFixes[indexCount], ja)
        return jsonObject.toString()

    }

    fun prepareLoginUserDataJson(uEmail: String?, uPassword: String?): String {
        var indexCount = 0
        val consKeyFixes = listOf("targetEmail", "targetPassword")
        val jsonObject = JSONObject()
        jsonObject.put(
            consKeyFixes[indexCount++],
            uEmail!!
        )
        jsonObject.put(
            consKeyFixes[indexCount],
            uPassword!!
        )
        return jsonObject.toString()
    }

    fun preparePasswordChangingJson(changingValueItself: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("password", changingValueItself)
        return jsonObject.toString()
    }

    fun prepareFlexibleJson(FilePath: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("thumbnail_image_url", FilePath)
        return jsonObject.toString()
    }


    fun prepareFlexibleJson(datas: List<String>): String {
        val consKeyFixes = listOf("label", "location", "period", "memo")
        val jsonObject = JSONObject()

        for (index in consKeyFixes.indices) {
            jsonObject.put(consKeyFixes[index], datas[index])
        }

        return jsonObject.toString()
    }

    fun prepareAccountJson(datas: List<String>, resultParamStringValue: String): String {
        val firstKeyFix = "uid"
        val consKeyFixes = listOf("email", "password", "nickname")
        val lastKeyFix = "point"
        val jsonObject = JSONObject()


        jsonObject.put(
            firstKeyFix,
            resultParamStringValue
        ) // auto increment value, string parsed from int value, change this later


        for (index in consKeyFixes.indices) {
            jsonObject.put(consKeyFixes[index], datas[index])
        }

        jsonObject.put(lastKeyFix, "0") // this is fixed value, 0

        return jsonObject.toString()
    }


    fun prepareJson(datas: List<String>): String {
        val jsonObject = JSONObject()

        val randValue = SecureRandom().nextInt(1000000)
        val randValueString = randValue.toString()
        val generatedCategoryId = "tplan_$randValueString"

        jsonObject.put("category_id", generatedCategoryId)
        jsonObject.put("label", datas[0])
        jsonObject.put("thumbnail_image_url", datas[4])
        jsonObject.put("updated", false)
        jsonObject.put("location", datas[1])
        jsonObject.put("period", datas[2])
        jsonObject.put("memo", datas[3])

        return jsonObject.toString()
    }

}
