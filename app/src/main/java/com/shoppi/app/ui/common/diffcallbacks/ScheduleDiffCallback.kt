package com.shoppi.app.ui.common.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import com.shoppi.app.model.Schedule

class ScheduleDiffCallback : DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem.namelabel == newItem.namelabel
    }

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem == newItem
    }

}