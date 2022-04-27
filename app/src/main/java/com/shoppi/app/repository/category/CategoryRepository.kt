package com.shoppi.app.repository.category

import com.shoppi.app.model.Category

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource
) {

    suspend fun getCategories(): List<Category> {
        val tmp: List<Category> = remoteDataSource.getCategories()
        for (item in tmp) {
            Supglobal.mSup += item.thumbnailImageUrl
            Supglobal.mSup += " "
        }
        Supglobal.mSup = Supglobal.mSup.dropLast(1)



        return tmp
    }
}