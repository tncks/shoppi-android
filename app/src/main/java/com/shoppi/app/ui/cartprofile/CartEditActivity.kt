package com.shoppi.app.ui.cartprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.shoppi.app.R
import com.shoppi.app.ui.common.factories.ViewModelFactory

class CartEditActivity : AppCompatActivity() {


    private val viewModel: CartEditViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_edit)



    }


}