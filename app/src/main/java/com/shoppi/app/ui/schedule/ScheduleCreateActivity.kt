package com.shoppi.app.ui.schedule

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityScheduleCreateBinding
import com.shoppi.app.ui.basewrapper.BaseActivity

class ScheduleCreateActivity :
    BaseActivity<ActivityScheduleCreateBinding>(R.layout.activity_schedule_create) {

    override fun initView() {
        super.initView()

        binding.apply {

            liveDataModel = viewModel
            lifecycleOwner = this@ScheduleCreateActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.tvSimpleCompleteEditSubmit.text = it.testOne
        }
    }
}