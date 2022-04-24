package com.shoppi.app.repository.category

import com.shoppi.app.model.Category

interface CategoryDataSource {
    suspend fun getCategories(): List<Category>
}