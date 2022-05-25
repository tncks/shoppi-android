package com.shoppi.app.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shoppi.app.model.Cart

class CartViewModel(

) : ViewModel() {

    private val _items = MutableLiveData<List<Cart>>()
    val items: LiveData<List<Cart>> = _items

    init {
        //dummy
        _items.postValue(
            listOf(
                Cart("1","엄마랑 제주도 여행",""),
                Cart("2","나 혼자 미국으로",""),
                Cart("3","엄마랑 제주도 여행",""),
                Cart("4","나 혼자 미국으로","")
            )
        )
    }
}

