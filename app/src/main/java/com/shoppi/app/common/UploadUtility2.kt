package com.shoppi.app.common

import android.util.Log
import android.webkit.MimeTypeMap
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class UploadUtility2 {

    private var isDoneAfterPath: String? = null
    private val serverURL = BACK_AZURE_STATIC_WEB_MEDIA_FILE_SERVER_URL
    private val serverUploadDirectoryPath = BACK_AZURE_STATIC_WEB_MEDIA_FILE_SERVER_IMAGE_DIR_URI
    private val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }


    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        // Coroutine Scope launch Dispatchers.IO 로 수정하고 적용, 테스트 및 디버그해서 동작하는지까지 확인 Bad Thread
        Thread {
            val mimeType = getMimeType(sourceFile)
            if (mimeType == null) {
                Log.i("dummy", "dummy")
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
                    Log.i("dummy", "dummy")
                }
            } catch (e: Exception) {
                e.printStackTrace()
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


