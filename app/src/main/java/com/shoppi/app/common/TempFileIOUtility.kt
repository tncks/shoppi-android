package com.shoppi.app.common

import android.content.Context
import android.net.Uri
import org.apache.commons.io.FileUtils
import java.io.File

class TempFileIOUtility {

    fun createFileFromAbsPath(context: Context, name: String, absolutePath: String): File {

        val stream = File(absolutePath).inputStream()
        val formatSuffix = ".jpg"  // only support jpg format
        val file = File.createTempFile(name, formatSuffix, context.cacheDir)
        FileUtils.copyInputStreamToFile(stream, file)

        return file

    }

    fun createFileFromUri(context: Context?, name: String, uri: Uri): File {

        val stream = context?.contentResolver?.openInputStream(uri)
        val formatSuffix = ".jpg"  // only support jpg format
        val file = File.createTempFile(name, formatSuffix, context?.cacheDir)
        FileUtils.copyInputStreamToFile(stream, file)

        return file

    }
}