package com.shoppi.app.ui.cartprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.databinding.FragmentCartProfileDetailBinding
import com.shoppi.app.databinding.FragmentCartProfileNameBinding
import com.shoppi.app.databinding.FragmentCategoryDetailBinding

class CartProfileDetailFragment : Fragment() {
    private lateinit var binding: FragmentCartProfileDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCartProfileDetailBinding.inflate(inflater,container,false)

        binding.ivLayout.setOnClickListener {
            findNavController().navigate(R.id.action_cart_profile_detail_to_cart_profile_laout)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }
}