package com.shoppi.app.ui.cartprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shoppi.app.model.Cart
import com.shoppi.app.model.Layout

class CartEditViewModel(

) : ViewModel() {
    private val _layout = MutableLiveData<Layout>()
    val layout : LiveData<Layout> = _layout

    init {

    }
    fun setLayout(layoutId : String, label : String, memo : String) {
        _layout.postValue(Layout(layoutId, label, memo))
    }
}

