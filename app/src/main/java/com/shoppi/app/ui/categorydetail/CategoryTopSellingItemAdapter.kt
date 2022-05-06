package com.shoppi.app.ui.categorydetail

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

    var nDayth: Int = 0          // should be public var, not private
    var selectionIndex: Int = 1
    var prevSelectionPointer: View? = null


    /*
    // in onCreateViewHolder block skip condition check because of same behavior
    // These comments are Description for condition
    // if   (viewType == CONTENT_TYPE) do same
    // elif (viewType == BUTTON_TYPE)  do same
    // else 404 do same
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingItemViewHolder {

        val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopSellingItemViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: TopSellingItemViewHolder, position: Int) {

        holder.bind(getItem(position))
    }


    override fun getItemViewType(position: Int): Int {

        if (itemCount == 1 && position == 0) {
            return 404
        }

        return if (position == itemCount - 1) {
            BUTTON_TYPE
        } else {
            CONTENT_TYPE
        }

    }

    /*-----------------------------------------------------------------------*/


    inner class TopSellingItemViewHolder(
        private val binding: ItemCategoryTopSellingBinding,
        private val viewType: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            when (viewType) {
                CONTENT_TYPE -> {
                    doContentTypeUiSetting(category)
                }
                BUTTON_TYPE -> {
                    doButtonTypeUiSetting()
                }
                else -> {
                    doButtonTypeUiSetting()
                }
            }

            // IMPORTANT FINALLY DO
            binding.executePendingBindings()
        }


        private fun doContentTypeUiSetting(category: Category) {

            nDayth++   // auto increment 처리 1일차, 2일차, 3일차 ... on and on
            binding.category = category

            binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label).setOnClickListener {
                if (prevSelectionPointer != null) {
                    (prevSelectionPointer as TextView).setTextColor(getIntegerBlackColorCode())
                }
                (it as TextView).setTextColor(getIntegerPrimaryColorCode())
                prevSelectionPointer = it
            }

            setInitialDefaultTextColorSelectedStyleOnFirst() // 처음에 한번만 실행되는 함수, 첫번째 1일차 색상 set 용도

            setStyleHideWithUnnecessaryImageView()

            setDayStringTextDaythAutoIncreDefaultOn(nDayth.toString())  // n일차 텍스트 설정 함수


        }

        private fun setInitialDefaultTextColorSelectedStyleOnFirst() {
            if (nDayth == selectionIndex) {
                binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label)
                    .setTextColor(getIntegerPrimaryColorCode())
                prevSelectionPointer =
                    (binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label) as View)
                selectionIndex = -10
            }
        }

        private fun setDayStringTextDaythAutoIncreDefaultOn(nDaythStringValue: String) {

            val resultJoinedStringValue = "$nDaythStringValue 일차"
            binding.root.findViewById<TextView>(R.id.tv_category_top_selling_label).text = resultJoinedStringValue

        }

        private fun doButtonTypeUiSetting() {

            val btnThumbnailImageUrl = "https://icon-library.com/images/add-icon-png/add-icon-png-25.jpg"
            setUpAddButtonImageAndUrl(btnThumbnailImageUrl)

            setStyleHideWithUnnecessaryTextView()

            binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image).setOnClickListener {
                doAddWork()
            }

        }

        // Util Functions public
        private fun getIntegerPrimaryColorCode(): Int {
            return Color.parseColor("#6970FD")
        }

        private fun getIntegerBlackColorCode(): Int {
            return Color.parseColor("#000000")
        }

        // End of util

        private fun setUpAddButtonImageAndUrl(btnThumbnailImageUrl: String) {
            binding.category = Category(
                "dummy",
                "  ",
                btnThumbnailImageUrl,
                false,
                "",
                "",
                ""
            )
        }

        private fun setStyleHideWithUnnecessaryImageView() {
            binding.root.findViewById<ImageView>(R.id.iv_category_top_selling_image).visibility = View.INVISIBLE
        }

        private fun setStyleHideWithUnnecessaryTextView() {
            binding.root.findViewById<TextView>(R.id.tv_category_date_period).visibility = View.INVISIBLE
        }

        private fun doAddWork() {

            Toast.makeText(binding.root.context, "add", Toast.LENGTH_SHORT).show()
            // do add process
            // start adding codes here

        }


    }

    /*-----------------------------------------------------------------------*/


}


/*-----------------------------------------------------------------------*/
/*-----------------------------------------------------------------------*/
// Reference for studying
// val tmpSelectionNameCurrent: String = ((it as TextView).text as String).dropLast(2)
// Toast.makeText(binding.root.context, "$tmpSelectionNameCurrent 일차", Toast.LENGTH_SHORT).show()
