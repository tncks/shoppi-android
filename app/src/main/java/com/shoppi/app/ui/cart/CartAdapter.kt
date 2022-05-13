package com.shoppi.app.ui.cart

import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.R
import com.shoppi.app.databinding.ItemCartBinding
import com.shoppi.app.databinding.ItemCategoryBinding
import com.shoppi.app.model.Cart
import com.shoppi.app.model.Category
import com.shoppi.app.ui.ProfileAddEditActivity
import com.shoppi.app.ui.common.CategoryDiffCallback

class CartAdapter(private val viewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val carts = mutableListOf<Cart>()

    fun updateCarts(carts : List<Cart>){
        this.carts.clear()
        this.carts.addAll(carts)
        notifyDataSetChanged() // 수정필요
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(carts[position])

        setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(holder, position)
    }


    /*-----------------------------------------------------------------*/

    private fun setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(
        holder: CartAdapter.CartViewHolder,
        position: Int
    ) {

        holder.itemView.setOnLongClickListener {

            val popup =
                PopupMenu(
                    holder.itemView.context,
                    holder.itemView,
                    Gravity.CENTER
                )
            popup.menuInflater.inflate(R.menu.list_item_popup_cart, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val menuIndex = when (item.itemId) {
                    R.id.mItem01 -> 0
                    R.id.mItem02 -> 1
                    else -> 1
                }
                when (menuIndex) {
                    0 -> {
//                        val mIntent = Intent(holder.binding.root.context, ProfileAddEditActivity::class.java)
//                        mIntent.putExtra("mIndex", position)
//                        holder.binding.root.context.startActivity(mIntent)
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

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: Cart) {
            binding.viewModel = viewModel
            binding.cart = cart
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return carts.size
    }

    /*-----------------------------------------------------------------*/


}
