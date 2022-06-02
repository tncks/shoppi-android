package com.shoppi.app.ui.cartprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.databinding.FragmentCartProfileDetailBinding
import com.shoppi.app.databinding.FragmentCartProfileNameBinding
import com.shoppi.app.databinding.FragmentCategoryDetailBinding

class CartProfileDetailFragment : Fragment() {
    private lateinit var binding: FragmentCartProfileDetailBinding
    private val viewModel : CartEditViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartProfileDetailBinding.inflate(inflater,container,false)

        binding.tvLayout.setOnClickListener {
            findNavController().navigate(R.id.action_cart_profile_detail_to_cart_profile_laout)
        }

        viewModel.layout.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,"${it.layoutId}번 레이아웃 선택되었음",Toast.LENGTH_SHORT).show()
            binding.tvLayout.text = "${it.layoutId}번 레이아웃"
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}