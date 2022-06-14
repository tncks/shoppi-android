package com.shoppi.app.common.bitmapcraft

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class BitmapConvertUtil {

    fun bitmapToStr(bitmap: Bitmap): String {
        val oStream =
            ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream)
        val bytes = oStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)

    }

    fun strToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(
                encodedString,
                Base64.DEFAULT
            )
            BitmapFactory.decodeByteArray(
                encodeByte,
                0,
                encodeByte.size
            )

        } catch (e: Exception) {
            e.message
            null
        }
    }
}