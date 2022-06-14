package com.shoppi.app.ui.signstep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shoppi.app.model.Termarea
import com.shoppi.app.ui.common.basewrapper.BaseViewModel

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