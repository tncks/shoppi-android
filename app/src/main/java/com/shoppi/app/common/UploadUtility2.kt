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
    var isSuccess = false

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null): Boolean {
        return uploadFile(File(sourceFilePath), uploadedFileName)
    }


    fun uploadFile(sourceFile: File, uploadedFileName: String? = null): Boolean {
        var isUploadedSuccessfully = false
        // Coroutine Scope launch Dispatchers.IO 로 수정하고 적용, 테스트 및 디버그해서 동작하는지까지 확인 Bad Thread
        val t = Thread {
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

                Log.d("echoecho", response.code.toString())
                if (response.isSuccessful) {
                    Log.d("uploadutility", "uploadFile: response isSuccessful")
                    // Log.d("echoecho", response.body.charStream())
                    val abcd = response.body
                    val abcde = abcd!!.string()
                    val abcdef = abcde.split("<")[0]
                    var lastnChars = abcdef
                    if (lastnChars.length > 2) {
                        lastnChars = lastnChars.substring(lastnChars.length - 2, lastnChars.length)
                    }
                    Log.d("echoecho", lastnChars)
                    isSuccess = if (lastnChars == "11") false else true


                    // isDoneAfterPath = "$serverUploadDirectoryPath$fileName"

                } else {
                    Log.i("dummy", "dummy")
                    isSuccess = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        t.start()
        t.join()
        val result = this.getterOfIsSuccess()
        return result
    }


    private fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }


    fun getterOfIsSuccess(): Boolean {
        return this.isSuccess
    }


}


