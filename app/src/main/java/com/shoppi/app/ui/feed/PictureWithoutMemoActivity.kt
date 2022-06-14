package com.shoppi.app.ui.feed

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityPictureWithoutMemoBinding
import com.shoppi.app.ui.common.basewrapper.BaseActivity

class PictureWithoutMemoActivity :
    BaseActivity<ActivityPictureWithoutMemoBinding>(R.layout.activity_picture_without_memo) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@PictureWithoutMemoActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.plainTextInput8.text = it.testOne
        }
    }
}