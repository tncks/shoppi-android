package com.shoppi.app.ui.cartprofile

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.R
import com.shoppi.app.databinding.ItemCartBinding
import com.shoppi.app.databinding.ItemLayoutBinding
import com.shoppi.app.model.Cart
import com.shoppi.app.model.Layout
import com.shoppi.app.ui.cartprofile.CartEditActivity

class LayoutAdapter() :
    RecyclerView.Adapter<LayoutAdapter.LayoutViewHolder>() {
    private val layouts = mutableListOf<Layout>()

    fun updateLayouts(layouts : List<Layout>){
        this.layouts.clear()
        this.layouts.addAll(layouts)
        notifyDataSetChanged() // 수정필요
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LayoutViewHolder(binding)
    }


    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        holder.bind(layouts[position])

    }

    inner class LayoutViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(layout: Layout) {
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return layouts.size
    }

    /*-----------------------------------------------------------------*/


}
