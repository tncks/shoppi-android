package com.shoppi.app.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.model.Category
import com.shoppi.app.repository.category.CategoryRepository
import com.shoppi.app.ui.category.CategoryBoolLiveArray
import com.shoppi.app.ui.common.Event
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet

class HistoryViewModel(
    @Suppress("unused") private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<Category>>()
    val items: LiveData<List<Category>> = _items

    private val _openHistoryEvent = MutableLiveData<Event<Category>>()
    val openHistoryEvent: LiveData<Event<Category>> = _openHistoryEvent

    var isNothingToShow: Boolean = false


    init {
        loadHistory()
    }

    fun openHistoryDetail(category: Category) {
        _openHistoryEvent.value = Event(category)
    }


    private fun loadHistory() {

        viewModelScope.launch {
            try {
                val connected = CheckInternet().check()
                if (connected) {
                    val categories: List<Category>? = categoryRepository.getCategories(SAFEUID)


                    categories?.let { totalArray ->

                        setObjectBoolLiveArrayData(totalArray)
                        val filteredCategories: List<Category> =
                            filterInCategoriesWithUpdatePropertyBoolTrueValueSelectAll(totalArray)
                        _items.value = filteredCategories

                        if (filteredCategories.isEmpty()) {
                            isNothingToShow = true
                        }

                    }

                } else {
                    Log.d("COCOCOCO", "OFFLINE")
                }
            } catch (e: Exception) {
                Log.d("erroronmodel", "errorthrowedOnViewmodelInit")
            }
        }

    }

    private fun filterInCategoriesWithUpdatePropertyBoolTrueValueSelectAll(categories: List<Category>): List<Category> {

        val filteredResults = mutableListOf<Category>()


        var i = 0
        for (category in categories) {
            if (category.updated) {
                filteredResults.add(category)
            }
            // else Log dummy
            i++
        }


        return filteredResults
    }

    private fun setObjectBoolLiveArrayData(categoriesParam: List<Category>) {
        CategoryBoolLiveArray.mUpdates.clear()
        for (category in categoriesParam) {
            CategoryBoolLiveArray.mUpdates.add(category.updated)
        }
    }

    fun externalViewReloadCallOnHistoryScreen() {
        loadHistory()
    }
}


