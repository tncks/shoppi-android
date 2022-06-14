@file:Suppress("EmptyMethod")

package com.shoppi.app.ui.common.basewrapper

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/****
 * ViewModel 있는 버전 템플릿, 확장 버전 (데이터 처리 가능)
 */


abstract class TemplateActivity<T : ViewDataBinding, R : ViewModel>
    (@LayoutRes private val layoutId: Int) : AppCompatActivity() {

    protected lateinit var binding: T
    protected lateinit var viewModel: R

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeSetContentView()

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        getViewModelClass()?.let {
            try {
                viewModel = ViewModelProvider(this).get(it)
            } catch (e: Exception) {
                Log.d("argumentmismatch","argumentmismatch")
                Log.d("isIninialQuest", "")
            }

        }


        initView()
        initViewModel()
        initListener()
        afterOnCreate()
    }

    protected open fun beforeSetContentView() {}
    protected open fun initView() {}
    protected open fun initViewModel() {}
    protected open fun initListener() {}
    protected open fun afterOnCreate() {}


    private fun getViewModelClass(): Class<R>? {
        try {
            val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
            @Suppress("UNCHECKED_CAST")
            return type as Class<R>
        } catch (e: Exception) {
            return null
        }

    }
}