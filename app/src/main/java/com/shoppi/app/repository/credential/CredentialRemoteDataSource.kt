package com.shoppi.app.repository.credential

import retrofit2.Response
import com.shoppi.app.network.TESTInterface
import okhttp3.RequestBody
import okhttp3.ResponseBody

class CredentialRemoteDataSource(private val myClient: TESTInterface) : CredentialDataSource {
    override suspend fun forwardEmail(requestBody: RequestBody): Response<ResponseBody> {
        return myClient.forwardEmail(requestBody)
    }

}