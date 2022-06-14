package com.shoppi.app.ui.schedule.subwork

import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import com.shoppi.app.R
import com.shoppi.app.databinding.ActivityScheduleCreateBinding
import com.shoppi.app.ui.common.basewrapper.TemplateActivity
import com.shoppi.app.ui.schedule.ScheduleDataModel

class ScheduleCreateActivity :
    TemplateActivity<ActivityScheduleCreateBinding, ScheduleDataModel>(R.layout.activity_schedule_create) {

    private var id: Long? = null

    override fun initView() {
        super.initView()

        binding.apply {
//            scheduleDataModel = viewModel
            lifecycleOwner = this@ScheduleCreateActivity
        }

        if (intent != null && intent.hasExtra(EXTRA_SCHEDULE_NAMELABEL) && intent.hasExtra(EXTRA_SCHEDULE_TIMERANGE)
            && intent.hasExtra(EXTRA_SCHEDULE_ID)
        ) {
            findViewById<EditText>(R.id.plain_text_input0).setText(intent.getStringExtra(EXTRA_SCHEDULE_NAMELABEL))
            findViewById<EditText>(R.id.plain_text_input2).setText(intent.getStringExtra(EXTRA_SCHEDULE_TIMERANGE))

            id = intent.getLongExtra(EXTRA_SCHEDULE_ID, -1)
        }

    }

    override fun initViewModel() {
        super.initViewModel()

/*
viewModel.items.observe(this) {
            Log.d("observe","observe")
            // binding.tvSimpleCompleteEditSubmit.text = it.testOne
        }
*/

    }

    override fun initListener() {
        super.initListener()

        binding.apply {
            tvSimpleCompleteEditSubmit.setOnClickListener {

                id = intent.getLongExtra(EXTRA_SCHEDULE_ID, -1)

                val prefs: SharedPreferences =
                    applicationContext.getSharedPreferences(
                        "scData",
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = prefs.edit()
                editor
                    .putLong("rowid", this@ScheduleCreateActivity.id!!)
                    .putString("namekeystr1", plainTextInput0.text.toString())
                    .putString("timekeystr2", plainTextInput2.text.toString())
                    .apply()

                finish()

            }
        }
    }

    companion object {
        const val EXTRA_SCHEDULE_NAMELABEL = "EXTRA_SCHEDULE_NAMELABEL"
        const val EXTRA_SCHEDULE_TIMERANGE = "EXTRA_SCHEDULE_TIMERANGE"
        const val EXTRA_SCHEDULE_ID = "EXTRA_SCHEDULE_ID"
    }
}