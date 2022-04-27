package com.shoppi.app.util

import android.view.View
import androidx.databinding.BindingAdapter

object MyBindingUtils {

    private const val ON_LONG_CLICK = "android:onLongClick"

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