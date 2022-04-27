package com.shoppi.app.petwork


import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {


    @PUT("categories/{number}.json")
    suspend fun updateCategories(@Path("number") number: String, @Body requestBody: RequestBody): Response<ResponseBody>

    @PATCH("categories/{number}.json")
    suspend fun updateItemProfileStyle(
        @Path("number") number: String,
        @Body requestBody: RequestBody
    ): Response<ResponseBody>

}