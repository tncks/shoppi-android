@file:Suppress("ReplaceManualRangeWithIndicesCalls")

package com.shoppi.app.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppi.app.common.BACK_AZURE_ACCOUNT_LOGIN_AUTH_RESTAPI_BASEURL
import com.shoppi.app.common.PrepareJsonHelper
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.model.Category
import com.shoppi.app.network.RESTInterface
import com.shoppi.app.repository.category.CategoryRepository
import com.shoppi.app.ui.common.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import xyz.teamgravity.checkinternet.CheckInternet

class CategoryViewModel(
    @Suppress("unused") private val categoryRepository: CategoryRepository
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
            try {
                val connected = CheckInternet().check()
                if (connected) {
                    val categories: List<Category>? = categoryRepository.getCategories(SAFEUID)

                    categories?.let { totalArray ->

                        setObjectBoolLiveArrayData(totalArray)
                        val filteredCategories: List<Category> =
                            filterInCategoriesWithUpdatePropertyBoolFalseValueSelectAll(totalArray)
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

    private fun filterInCategoriesWithUpdatePropertyBoolFalseValueSelectAll(categories: List<Category>): List<Category> {

        val filteredResults = mutableListOf<Category>()


        var i = 0
        for (category in categories) {
            if (category.updated) {
                if (i == -2) {
                    Log.i("dummy", "dummy") // dummy code
                }
                // Log.d("updatedValueTAt",i.toString()) // not dummy and debug code
            } else {
                filteredResults.add(category)
            }
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

    fun updateFakeCategoryTmp(b: List<Int>, tmpAbsolutes: MutableList<Int>) {
        val originals = _items.value
        val realOrigin = originals?.toMutableList()
        for (i in 0 until b.size) {
            realOrigin?.removeAt(tmpAbsolutes[b[b.size - 1 - i]])
        }

        _items.value = realOrigin

        /*---------------------------------------------------*/


        viewModelScope.launch {
            val retrofit = Retrofit.Builder()
                .baseUrl(BACK_AZURE_ACCOUNT_LOGIN_AUTH_RESTAPI_BASEURL)
                .build()
            val service = retrofit.create(RESTInterface::class.java)
            val sa = mutableListOf<String>()

            for (i in 0 until b.size) {
                sa.add((tmpAbsolutes[b[b.size - 1 - i]]).toString())
            }

            for (i in 0 until b.size) {

                val jsonObjectString: String = PrepareJsonHelper().prepareCleanRemoveIndexingJson(sa[i])

                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


                val njsResponse = service.deleteCategories(requestBody)

                try {
                    val isNjsResultSuccessful = when (njsResponse.code()) {
                        200 -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }

                    if (isNjsResultSuccessful && (i == b.size - 1)) {
                        delay(1500L)
                        val categories: List<Category>? = categoryRepository.getCategories(SAFEUID)
                        SelectionAllToggleSelector.singleToneFlag = true

                        categories?.let { totalArray ->

                            val filteredCategories: List<Category> =
                                filterInCategoriesWithUpdatePropertyBoolFalseValueSelectAll(totalArray)
                            _items.value = filteredCategories

                            if (filteredCategories.isEmpty()) {
                                isNothingToShow = true
                            }

                        }

                    } else {
                        delay(500L)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }


    }

    fun externalReloadInitAgainOnOnlineStatus() {
        loadCategory()
    }

}


// Reference for studying
/*--------------------------------------------------------------*/
/*--------------------------------------------------------------*/
/*
    for future use, like gesture condition flow, when need use this

    fun onLongClick(category: Category) {
        _openCategoryEvent2.value = Event(category)
    }
*/