package com.shoppi.app.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.model.Category
import com.shoppi.app.repository.category.CategoryRepository
import com.shoppi.app.ui.common.Event
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Category>>()
    val items: LiveData<List<Category>> = _items

    private val _openCategoryEvent = MutableLiveData<Event<Category>>()
    val openCategoryEvent: LiveData<Event<Category>> = _openCategoryEvent

    var isNothingToShow: Boolean = false


    init {
        loadCategory()
    }

    fun openCategoryDetail(category: Category) {
        _openCategoryEvent.value = Event(category)
    }


    private fun loadCategory() {
        viewModelScope.launch {
            val categories: List<Category>? = categoryRepository.getCategories(SAFEUID)
            // change this line null sentence to isNotEmpty() when need
            if (categories == null) {
                Log.i("dummy", "dummy")
            } else {
                Log.i("dummy", "dummy")
                _items.value = categories

                if (categories.isEmpty()) {
                    isNothingToShow = true
                }
            }


        }
    }
}


/*--------------------------------------------------------------*/
/*--------------------------------------------------------------*/
/*
             for future use, like gesture condition flow, when need use this

    fun onLongClick(category: Category) {
        _openCategoryEvent2.value = Event(category)
    }
*/