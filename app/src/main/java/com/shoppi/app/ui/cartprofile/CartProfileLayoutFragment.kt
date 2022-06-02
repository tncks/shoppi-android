package com.shoppi.app.ui.cartprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shoppi.app.databinding.FragmentCartProfileDetailBinding
import com.shoppi.app.databinding.FragmentCartProfileLayoutBinding
import com.shoppi.app.databinding.FragmentCartProfileNameBinding
import com.shoppi.app.databinding.FragmentCategoryDetailBinding
import com.shoppi.app.model.Cart
import com.shoppi.app.model.Layout

class CartProfileLayoutFragment : Fragment() {
    private lateinit var binding: FragmentCartProfileLayoutBinding
    private val viewModel : CartEditViewModel by activityViewModels()
    private lateinit var adapter : LayoutAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartProfileLayoutBinding.inflate(inflater,container,false)
        adapter = LayoutAdapter(viewModel)
        binding.rvLayout.adapter = adapter
        setAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }
    private fun setAdapter(){
        listOf(
            Layout("1","레이아웃 1",""),
            Layout("2","레이아웃 2",""),
            Layout("3","레이아웃 3",""),
            Layout("4","레이아웃 4",""),
            Layout("5","레이아웃 5",""),
            Layout("6","레이아웃 6",""),
            Layout("7","레이아웃 7",""),
            Layout("8","레이아웃 8","")
        ).apply {
            adapter.updateLayouts(this)
        }
    }
}