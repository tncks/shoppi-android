package com.shoppi.app.ui.categorydetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.R
import com.shoppi.app.common.BUTTON_TYPE
import com.shoppi.app.common.CONTENT_TYPE
import com.shoppi.app.databinding.ItemCategoryTopSellingBinding
import com.shoppi.app.model.Category
import com.shoppi.app.ui.common.CategoryDiffCallback

class CategoryTopSellingItemAdapter :
    ListAdapter<Category, CategoryTopSellingItemAdapter.TopSellingItemViewHolder>(CategoryDiffCallback()) {

    var nDayth: Int = 0
    var selectionIndex: Int = 1
    var prevSelectionPointer: View? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingItemViewHolder {

        val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopSellingItemViewHolder(binding, viewType)

//        if (viewType == CONTENT_TYPE) {
//
//            val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return TopSellingItemViewHolder(binding, viewType)
//
//        } else if (viewType == BUTTON_TYPE) {
//
//
//            val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return TopSellingItemViewHolder(binding, viewType)
//
//        } else {
//            val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            return TopSellingItemViewHolder(binding, viewType)
//        }


    }

    override fun onBindViewHolder(holder: TopSellingItemViewHolder, position: Int) {

        holder.bind(getItem(position))

    }


    override fun getItemViewType(position: Int): Int {

        if (itemCount == 1 && position == 0) {
            return 404
        }

        if (position == itemCount - 1) {
            return BUTTON_TYPE
        } else {
            return CONTENT_TYPE
        }

    }


    inner class TopSellingItemViewHolder(
        private val binding: ItemCategoryTopSellingBinding,
        private val viewType: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "CutPasteId")
        fun bind(category: Category) {
            if (viewType == CONTENT_TYPE) {
                nDayth++
                binding.category = category
                binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label).setOnClickListener {
                    if (prevSelectionPointer != null) {
                        (prevSelectionPointer as TextView).setTextColor(Color.parseColor("#000000"))
                    }
                    // val tmpSelectionNameCurrent: String = ((it as TextView).text as String).dropLast(2)
                    // Toast.makeText(binding.root.context, "$tmpSelectionNameCurrent 일차", Toast.LENGTH_SHORT).show()
                    (it as TextView).setTextColor(Color.parseColor("#6970FD"))
                    prevSelectionPointer = it
                }
                if (nDayth == selectionIndex) {
                    binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label)
                        .setTextColor(Color.parseColor("#6970FD"))
                    prevSelectionPointer =
                        (binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label) as View)
                    selectionIndex = -10
                }

                binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image).visibility = View.INVISIBLE
                binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label).text =
                    (nDayth.toString() + "일차")
                binding.executePendingBindings()
            } else if (viewType == BUTTON_TYPE) {
                binding.category = Category(
                    "dummy",
                    "  ",
                    "https://icon-library.com/images/add-icon-png/add-icon-png-25.jpg",
                    false,
                    "",
                    "",
                    ""
                )
                binding.root.findViewById<TextView>(R.id.tv_category_date_period).visibility = View.INVISIBLE
                binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image)
                    .setImageResource(R.drawable.ic_plus)
                binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image).setOnClickListener {
                    Toast.makeText(binding.root.context, "add", Toast.LENGTH_SHORT).show()
                }
                binding.executePendingBindings()
            } else {
                binding.category = Category(
                    "otherdummy",
                    "  ",
                    "https://icon-library.com/images/add-icon-png/add-icon-png-25.jpg",
                    false,
                    "",
                    "",
                    ""
                )
                binding.root.findViewById<TextView>(R.id.tv_category_date_period).visibility = View.INVISIBLE
                binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image)
                    .setImageResource(R.drawable.ic_plus)
                binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image).setOnClickListener {
                    Toast.makeText(binding.root.context, "add", Toast.LENGTH_SHORT).show()
                }
                binding.executePendingBindings()
            }

        }


    }


}

