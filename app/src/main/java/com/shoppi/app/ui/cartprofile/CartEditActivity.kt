package com.shoppi.app.ui.cartprofile

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.shoppi.app.R
import com.shoppi.app.common.BFLAG
import com.shoppi.app.ui.cart.CartViewModel
import com.shoppi.app.ui.common.ViewModelFactory

class CartEditActivity : AppCompatActivity() {


    private val viewModel: CartEditViewModel by viewModels { ViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_edit)



    }


}