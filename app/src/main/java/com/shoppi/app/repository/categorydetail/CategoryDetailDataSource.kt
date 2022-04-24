package com.shoppi.app.repository.categorydetail

import com.shoppi.app.model.CategoryDetail

interface CategoryDetailDataSource {

    suspend fun getCategoryDetail(): CategoryDetail
}