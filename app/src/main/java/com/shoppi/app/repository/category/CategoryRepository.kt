package com.shoppi.app.repository.category

import com.shoppi.app.model.Category

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource
) {

    suspend fun getCategories(): List<Category> {

        return remoteDataSource.getCategories()
    }
}