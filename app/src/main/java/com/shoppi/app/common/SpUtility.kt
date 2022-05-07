@file:Suppress("unused")

package com.shoppi.app.common

import android.content.Context

class SpUtility(private val context: Context) {
    /*

    fun myInitSup(): Int {
        val preferences: SharedPreferences? =
            context.getSharedPreferences("imagesNumByUserPicked", Context.MODE_PRIVATE)
        val iNumPicked: Int? = preferences?.getInt("imagesNumByUserPicked", 0)
        var safeReturnSameParam = 0

        if (iNumPicked != null) {
            safeReturnSameParam = iNumPicked
        }

        return safeReturnSameParam
    }

    fun myInitSup2(): MutableList<String> {
        val preferences: SharedPreferences? =
            context.getSharedPreferences("myMutables", Context.MODE_PRIVATE)
        val resultI: MutableList<String>? = preferences?.getStringSet(
            "myMutables", setOf(
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://img.freepik.com/free-photo/euphorbia-milii-red-flower-green-leaf-with-sunlight-background_34056-582.jpg",
                "https://image.shutterstock.com/image-illustration/drawing-beautiful-purple-flower-on-260nw-317434271.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg"
            )
        )?.toMutableList()
        var resultParam: MutableList<String> = mutableListOf(
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://img.freepik.com/free-photo/euphorbia-milii-red-flower-green-leaf-with-sunlight-background_34056-582.jpg",
            "https://image.shutterstock.com/image-illustration/drawing-beautiful-purple-flower-on-260nw-317434271.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg"
        )

        if (resultI != null) {
            resultParam = resultI
        } else {
            return resultParam
        }

        return resultParam
    }

    */
}