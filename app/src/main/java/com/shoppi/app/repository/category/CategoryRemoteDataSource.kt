package com.shoppi.app.repository.category

import com.shoppi.app.model.Category
import com.shoppi.app.network.ApiClient

class CategoryRemoteDataSource(private val apiClient: ApiClient) : CategoryDataSource {
    override suspend fun getCategories(uid: String): List<Category>? {
        return apiClient.getCategories(uid)
    }

}