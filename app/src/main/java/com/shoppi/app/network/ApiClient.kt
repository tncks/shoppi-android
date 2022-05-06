@file:Suppress("UnnecessaryVariable", "ConvertTryFinallyToUseCall")

package com.shoppi.app.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.model.Category
import com.shoppi.app.model.CategoryDetail
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type

interface ApiClient {

    @GET("users/{uid}/categories.json")
    suspend fun getCategories(@Path("uid") uid: String): List<Category>?

    @GET("fashion_female.json")
    suspend fun getCategoryDetail(): CategoryDetail


    companion object {

        private const val baseUrl = FIRE_JSON_BASEURL

        fun create(): ApiClient {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()


            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)


        }

        class NullOnEmptyConverterFactory : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                val gson = Gson()
                val adapter = gson.getAdapter(TypeToken.get(type))

                return object : Converter<ResponseBody, Any> {
                    override fun convert(body: ResponseBody): Any? {
                        try {
                            if (body.contentLength() == 4L) {
                                return listOf<Category>()
                            }
                            val jsonReader = gson.newJsonReader(body.charStream())
                            val result = adapter.read(jsonReader)

                            return result
                        } finally {
                            body.close()
                        }
                    }
                }
            }
        }
    }
}


