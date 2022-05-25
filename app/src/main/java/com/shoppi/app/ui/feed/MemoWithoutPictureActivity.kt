package com.shoppi.app.ui.feed

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityMemoWithoutPictureBinding
import com.shoppi.app.ui.basewrapper.BaseActivity

class MemoWithoutPictureActivity :
    BaseActivity<ActivityMemoWithoutPictureBinding>(R.layout.activity_memo_without_picture) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@MemoWithoutPictureActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()

        
        viewModel.isUpdate.observe(this) {
            binding.plainTextInput3.setText(it.testOne)
        }
    }
}
