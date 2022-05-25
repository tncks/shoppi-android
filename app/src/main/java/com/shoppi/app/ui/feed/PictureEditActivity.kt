package com.shoppi.app.ui.feed

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityPictureEditBinding
import com.shoppi.app.ui.basewrapper.BaseActivity

class PictureEditActivity :
    BaseActivity<ActivityPictureEditBinding>(R.layout.activity_picture_edit) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@PictureEditActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.unrealgonetv.text = it.testOne
        }
    }
}