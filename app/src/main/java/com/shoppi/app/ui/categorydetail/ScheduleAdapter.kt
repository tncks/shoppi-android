package com.shoppi.app.ui.categorydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.databinding.ItemScheduleWeekBinding
import com.shoppi.app.model.Schedule
import com.shoppi.app.ui.common.ScheduleDiffCallback

class ScheduleAdapter(private val viewModel: CategoryDetailViewModel) :
    ListAdapter<Schedule, ScheduleAdapter.ScheduleItemViewHolder>(ScheduleDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val binding = ItemScheduleWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleItemViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ScheduleItemViewHolder(
        private val binding: ItemScheduleWeekBinding,
        private val viewType: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: Schedule) {
            binding.viewModel = viewModel
            binding.schedule = schedule
            binding.executePendingBindings()
        }

    }


}
