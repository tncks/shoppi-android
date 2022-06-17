package com.shoppi.app.ui.schedule.placemap

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityTheLocationBinding
import com.shoppi.app.ui.common.basewrapper.BaseActivity

class TheLocationActivity :
    BaseActivity<ActivityTheLocationBinding>(R.layout.activity_the_location) {

    override fun initView() {
        super.initView()

        binding.apply {
            liveDataModel = viewModel
            lifecycleOwner = this@TheLocationActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.locationaddress.setText(it.testOne)
        }
    }
}