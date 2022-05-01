package com.shoppi.app.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.shoppi.app.common.ON_LONG_CLICK

object MyBindingUtils {

    @JvmStatic
    @BindingAdapter(ON_LONG_CLICK)
    fun setOnLongClickListener(
        view: View,
        func: () -> Unit
    ) {
        view.setOnLongClickListener {
            func()
            return@setOnLongClickListener true
        }
    }
}