package com.shoppi.app.common

import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class UploadUtility2 {

    private var isDoneAfterPath: String? = null
    private val serverURL = "https://agile-savannah-32015.herokuapp.com/index.php"
    private val serverUploadDirectoryPath = "https://agile-savannah-32015.herokuapp.com/images/"
    private val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }


    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {
                val typeDummy = 0
                Log.i("dummy", typeDummy.toString())
                return@Thread
            }
            val fileName: String = (uploadedFileName ?: sourceFile.name)

            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart(
                            "imgFile",
                            fileName,
                            sourceFile.asRequestBody(mimeType.toMediaTypeOrNull())
                        )
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    isDoneAfterPath = "$serverUploadDirectoryPath$fileName"

                } else {
                    val realDummy = 0
                    Log.i("dummy", realDummy.toString())


                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                val errorDummy = 0
                Log.i("dummy", errorDummy.toString())

            }

        }.start()
    }


    private fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }


}


