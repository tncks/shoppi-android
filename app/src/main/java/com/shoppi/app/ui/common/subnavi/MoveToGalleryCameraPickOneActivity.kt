package com.shoppi.app.ui.common.subnavi

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityMoveToGalleryCameraPickOneBinding
import com.shoppi.app.ui.common.basewrapper.BaseActivity

class MoveToGalleryCameraPickOneActivity: BaseActivity<ActivityMoveToGalleryCameraPickOneBinding>(R.layout.activity_move_to_gallery_camera_pick_one) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@MoveToGalleryCameraPickOneActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.tvMoveToCamera.text = it.testOne
        }
    }


}