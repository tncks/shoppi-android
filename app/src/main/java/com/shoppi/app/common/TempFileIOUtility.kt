package com.shoppi.app.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream

class TempFileIOUtility {

    fun createFileFromAbsPath(context: Context, name: String, absolutePath: String): File {

        val stream = File(absolutePath).inputStream()
        val formatSuffix = ".jpg"
        val file = File.createTempFile(name, formatSuffix, context.cacheDir)
        FileUtils.copyInputStreamToFile(stream, file)

        Log.d("lenlenfilepath", file.path)
        Log.d("lenlenfilename", file.name)

        return file

    }

    fun createFileFromUri(context: Context?, name: String, uri: Uri): File {

        var file: File? = null
        var fos: FileOutputStream? = null
        fos = FileOutputStream(File.createTempFile("iniName", ".jpg", context?.cacheDir))
        try {
            val formatSuffix = ".jpg"  // only support jpg format
            file = File.createTempFile(name, formatSuffix, context?.cacheDir)

            fos.close()
            fos = null
            fos = FileOutputStream(file)

            val bitmap: Bitmap? = if (context != null) resizeBitmapFormUri(uri, context) else null
            if (bitmap == null) {
                return File.createTempFile("errorDesc", ".jpg", context?.cacheDir)
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            bitmap.recycle()

            fos.flush()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fos?.close()
        }

        if (file != null) {
            return file // write and compress 가 완료된 임시파일을 리턴
        } else {
            return File.createTempFile("errorDesc", ".jpg", context?.cacheDir)
        }

        // Reference for studying
        // val stream = context?.contentResolver?.openInputStream(uri) // original file, read from origin -> stream에 저장
        // FileUtils.copyInputStreamToFile(stream, file) // stream의 내용을 복사해서, target file에 write


    }

    private fun resizeBitmapFormUri(uri: Uri, context: Context): Bitmap? {
        val input = context.contentResolver.openInputStream(uri)

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        var bitmap: Bitmap?
        BitmapFactory.Options().run {
            inSampleSize = calculateInSampleSize(options)
            bitmap = BitmapFactory.decodeStream(input, null, this)
        }

        bitmap = bitmap?.let {
            rotateImageIfRequired(context, bitmap!!, uri)
        }

        input?.close()

        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        var height = options.outHeight
        var width = options.outWidth

        var inSampleSize = 1

        while (height > MAX_HEIGHT || width > MAX_WIDTH) {
            height /= 2
            width /= 2
            inSampleSize *= 2
        }

        return inSampleSize
    }

    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif = if (Build.VERSION.SDK_INT > 23) {
            ExifInterface(input)
        } else {
            ExifInterface(uri.path!!)
        }

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    companion object {
        const val MAX_WIDTH = 1440
        const val MAX_HEIGHT = 1050
    }
}

