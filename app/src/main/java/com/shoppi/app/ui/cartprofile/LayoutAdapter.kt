package com.shoppi.app.ui.cartprofile

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.R
import com.shoppi.app.databinding.ItemCartBinding
import com.shoppi.app.databinding.ItemLayoutBinding
import com.shoppi.app.model.Cart
import com.shoppi.app.model.Layout
import com.shoppi.app.ui.cartprofile.CartEditActivity

class LayoutAdapter(viewModel : ViewModel) :
    RecyclerView.Adapter<LayoutAdapter.LayoutViewHolder>() {
    private val layouts = mutableListOf<Layout>()
    private val cartEditViewModel = viewModel as CartEditViewModel

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
        val layout = layouts[position]
        holder.bind(layout)
        holder.itemView.setOnClickListener {
            cartEditViewModel.setLayout(layout.layoutId, layout.label, layout.memo)
            holder.binding.root.findNavController().navigateUp()
        }
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
