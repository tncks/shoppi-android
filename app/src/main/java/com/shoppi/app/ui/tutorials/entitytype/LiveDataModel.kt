package com.shoppi.app.ui.tutorials.entitytype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shoppi.app.ui.basewrapper.BaseViewModel
import com.shoppi.app.ui.tutorials.datapool.Option

// member param private val repository version need template or child에서 따로 개별적으로 각각 구현
class LiveDataModel : BaseViewModel() {

    private val _isUpdate = MutableLiveData<Option>()

    val isUpdate: LiveData<Option>
        get() = _isUpdate

    @Suppress("unused")
    fun setText(text: Option) {
        _isUpdate.value = text
    }

    /*
    private fun loadData() {
        // viewModelScope.launch { val resResult = repository.getOrPostData() and items.value = resResult }
    }

    init {
        loadData()
    }
     */

}