package com.shoppi.app.repository.categorydetail

import com.shoppi.app.model.CategoryDetail

class CategoryDetailRepository(private val remoteDataSource: CategoryDetailRemoteDataSource) {

    suspend fun getCategoryDetail(): CategoryDetail {
        return remoteDataSource.getCategoryDetail()
    }
}