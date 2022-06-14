package com.shoppi.app.ui.common.subnavi

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityMoveToGalleryCameraPickOneWithNextBinding
import com.shoppi.app.ui.common.basewrapper.BaseActivity


class MoveToGalleryCameraPickOneWithNextActivity: BaseActivity<ActivityMoveToGalleryCameraPickOneWithNextBinding>(R.layout.activity_move_to_gallery_camera_pick_one_with_next) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@MoveToGalleryCameraPickOneWithNextActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.tvMoveToCamera.text = it.testOne
        }
    }


}