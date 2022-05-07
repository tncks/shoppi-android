package com.shoppi.app.repository.categorydetail

import com.shoppi.app.model.CategoryDetail
import com.shoppi.app.network.ApiClient

class CategoryDetailRemoteDataSource(private val apiClient: ApiClient) : CategoryDetailDataSource {
    override suspend fun getCategoryDetail(): CategoryDetail {
        return apiClient.getCategoryDetail()
    }

}