package com.shoppi.app.repository.category

import com.shoppi.app.model.Category

class CategoryRepository(
    private val remoteDataSource: CategoryRemoteDataSource
) {

    suspend fun getCategories(): List<Category> {
        val tmp: List<Category> = remoteDataSource.getCategories()

        /*------------------------------------------------------*/
        /*------------------------------------------------------*/
        Supglobal.mSup = ""
        Supglobal.mLabel = ""
        Supglobal.mLocation = ""
        Supglobal.mPeriod = ""
        Supglobal.mMemo = ""
        /*----------------------*/
        for (item in tmp) {
            Supglobal.mSup += item.thumbnailImageUrl
            Supglobal.mSup += "|"

            Supglobal.mLabel += item.label
            Supglobal.mLabel += "|"

            Supglobal.mLocation += item.location
            Supglobal.mLocation += "|"

            Supglobal.mPeriod += item.period
            Supglobal.mPeriod += "|"

            Supglobal.mMemo += item.memo
            Supglobal.mMemo += "|"
        }
        /*----------------------*/
        Supglobal.mSup = Supglobal.mSup.dropLast(1)
        Supglobal.mLabel = Supglobal.mLabel.dropLast(1)
        Supglobal.mLocation = Supglobal.mLocation.dropLast(1)
        Supglobal.mPeriod = Supglobal.mPeriod.dropLast(1)
        Supglobal.mMemo = Supglobal.mMemo.dropLast(1)
        /*------------------------------------------------------*/
        /*------------------------------------------------------*/


        return tmp
    }
}