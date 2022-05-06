package com.shoppi.app.ui.category

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.R
import com.shoppi.app.databinding.ItemCategoryBinding
import com.shoppi.app.model.Category
import com.shoppi.app.ui.ProfileAddEditActivity
import com.shoppi.app.ui.common.CategoryDiffCallback

class CategoryAdapter(private val viewModel: CategoryViewModel) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))

        setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(holder, position)
    }


    /*-----------------------------------------------------------------*/

    private fun setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(
        holder: CategoryAdapter.CategoryViewHolder,
        position: Int
    ) {

        holder.itemView.setOnLongClickListener {

            val popup =
                PopupMenu(
                    holder.itemView.context,
                    holder.itemView,
                    Gravity.CENTER
                )
            popup.menuInflater.inflate(R.menu.list_item_popup, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val menuIndex = when (item.itemId) {
                    R.id.mItem01 -> 0
                    R.id.mItem02 -> 1
                    else -> 2
                }
                when (menuIndex) {
                    0 -> {
                        val mIntent = Intent(holder.binding.root.context, ProfileAddEditActivity::class.java)
                        mIntent.putExtra("mIndex", position)
                        holder.binding.root.context.startActivity(mIntent)
                    }
                    1 -> {
                        Log.i("dummy", "dummy")
                    }
                    2 -> {
                        Log.i("dummy", "dummy")
                    }
                }
                true
            }
            popup.show()
            true
        }

    }

    /*-----------------------------------------------------------------*/

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.viewModel = viewModel
            binding.category = category
            binding.executePendingBindings()
        }
    }

    /*-----------------------------------------------------------------*/


}
