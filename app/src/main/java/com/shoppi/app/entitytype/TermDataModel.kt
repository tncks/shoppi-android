package com.shoppi.app.entitytype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shoppi.app.datapool.Termarea
import com.shoppi.app.ui.basewrapper.BaseViewModel

class TermDataModel : BaseViewModel() {

    private val _isUpdate = MutableLiveData<Termarea>()

    val isUpdate: LiveData<Termarea>
        get() = _isUpdate

    @Suppress("unused")
    fun setText(text: Termarea) {
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