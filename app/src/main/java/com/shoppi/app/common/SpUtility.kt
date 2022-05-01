package com.shoppi.app.common

import android.content.Context
import android.content.SharedPreferences

class SpUtility(private val context: Context) {
    fun myInitSup(): Int {
        val preferences: SharedPreferences? =
            context.getSharedPreferences("imagesNumByUserPicked", Context.MODE_PRIVATE)
        val resultI: Int? = preferences?.getInt("imagesNumByUserPicked", 0)
        var resultParam = 0

        if (resultI != null) {
            resultParam = resultI
        }

        return resultParam
    }

    fun myInitSup2(): MutableList<String> {
        val preferences: SharedPreferences? =
            context.getSharedPreferences("myMutables", Context.MODE_PRIVATE)
        val resultI: MutableList<String>? = preferences?.getStringSet(
            "myMutables", setOf<String>(
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
                "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg"
            )
        )?.toMutableList()
        var resultParam: MutableList<String> = mutableListOf<String>(
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
            "https://webkit.org/blog-files/color-gamut/YellowFlower-sRGB.jpg",
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
}