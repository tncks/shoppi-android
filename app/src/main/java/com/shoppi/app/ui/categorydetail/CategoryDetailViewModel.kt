package com.shoppi.app.ui.categorydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppi.app.model.TopSelling
import com.shoppi.app.repository.categorydetail.CategoryDetailRepository
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet

class CategoryDetailViewModel(
    private val categoryDetailRepository: CategoryDetailRepository
) : ViewModel() {

    private val _topSelling = MutableLiveData<TopSelling>()
    val topSelling: LiveData<TopSelling> = _topSelling

    init {
        loadCategoryDetail()
    }



    private fun loadCategoryDetail() {

        viewModelScope.launch {
            try {
                if (CheckInternet().check()) {
                    val categoryDetail = categoryDetailRepository.getCategoryDetail()
                    _topSelling.value = categoryDetail.topSelling
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }



}