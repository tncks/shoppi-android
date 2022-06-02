package com.shoppi.app.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoppi.app.network.ApiClient
import com.shoppi.app.repository.category.CategoryRemoteDataSource
import com.shoppi.app.repository.category.CategoryRepository
import com.shoppi.app.repository.categorydetail.CategoryDetailRemoteDataSource
import com.shoppi.app.repository.categorydetail.CategoryDetailRepository
import com.shoppi.app.ui.cart.CartViewModel
import com.shoppi.app.ui.cartprofile.CartEditViewModel
import com.shoppi.app.ui.category.CategoryViewModel
import com.shoppi.app.ui.categorydetail.CategoryDetailViewModel
import com.shoppi.app.ui.history.HistoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                val repository = CategoryRepository(CategoryRemoteDataSource(ApiClient.create()))
                @Suppress("UNCHECKED_CAST")
                CategoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CategoryDetailViewModel::class.java) -> {
                val repository = CategoryDetailRepository(CategoryDetailRemoteDataSource(ApiClient.create()))
                @Suppress("UNCHECKED_CAST")
                CategoryDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                CartViewModel() as T
            }
            modelClass.isAssignableFrom(CartEditViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                CartEditViewModel() as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                val repository = CategoryRepository(CategoryRemoteDataSource(ApiClient.create()))
                @Suppress("UNCHECKED_CAST")
                HistoryViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}