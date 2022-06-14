package com.shoppi.app.ui.feed

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityPictureWithMemoBinding
import com.shoppi.app.ui.common.basewrapper.BaseActivity

class PictureWithMemoActivity : BaseActivity<ActivityPictureWithMemoBinding>(R.layout.activity_picture_with_memo) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@PictureWithMemoActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.plainTextInput9.setText(it.testOne)
        }
    }
}