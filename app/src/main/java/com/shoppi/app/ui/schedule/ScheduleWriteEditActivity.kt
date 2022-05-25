package com.shoppi.app.ui.schedule

import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityScheduleWriteEditBinding
import com.shoppi.app.ui.basewrapper.BaseActivity

class ScheduleWriteEditActivity :
    BaseActivity<ActivityScheduleWriteEditBinding>(R.layout.activity_schedule_write_edit) {

    override fun initView() {
        super.initView()

        binding.apply {

            liveDataModel = viewModel
            lifecycleOwner = this@ScheduleWriteEditActivity
        }

    }

    override fun initViewModel() {
        super.initViewModel()


        viewModel.isUpdate.observe(this) {
            binding.tvSimpleCompleteEditSubmit.text = it.testOne
        }
    }
}