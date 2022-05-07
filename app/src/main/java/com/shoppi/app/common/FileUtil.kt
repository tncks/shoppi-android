package com.shoppi.app.common

/*
class FileUtil {

    fun optimizeBitmap(context: Context, uri: Uri): Boolean { // String?
        try {
            val fileName = String.format("%s.%s", UUID.randomUUID(), ".jpg")
            val file = File(context.cacheDir, fileName)
            file.createNewFile()

            val fos = FileOutputStream(file)

            val bitmap = resizeBitmapFormUri(uri, context)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos) // 100%로 압축
            bitmap?.recycle()

            fos.flush()
            fos.close()

            // upload logic here

            return true // file.absolutePath 최적화된 임시 이미지 파일의 절대경로
        } catch (e: OutOfMemoryError) {
            Log.e(TAG, "OutOfMemoryError: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            Log.e(TAG, "Exception - ${e.message}")
            e.printStackTrace()
        }

        return false // null
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
*/