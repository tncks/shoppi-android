package com.shoppi.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shoppi.app.R
import com.shoppi.app.databinding.FragmentHomeBinding
import com.shoppi.app.ui.LastingActivity
import com.shoppi.app.ui.ProfileAddEditActivity
import com.shoppi.app.ui.common.ViewModelFactory
import com.shoppi.app.ui.signstep.SignBeforeStartActivity

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setTopBanners()
        setTestButtonEvent(view)
        setTestButtonEventSecond(view)
        setTestButtonEventThird(view)

    }

    private fun setToolbar() {
        @Suppress("MoveLambdaOutsideParentheses")
        viewModel.title.observe(viewLifecycleOwner, { title ->
            binding.title = title
        })
    }

    private fun setTopBanners() {
        with(binding.viewpagerHomeBanner) {
            adapter = HomeBannerAdapter().apply {
                @Suppress("MoveLambdaOutsideParentheses")
                viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
                    submitList(banners)
                })
            }

            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth - pageWidth - pageMargin

            offscreenPageLimit = 3
            setPageTransformer { page, position ->
                page.translationX = position * -offset
            }

            TabLayoutMediator(
                binding.viewpagerHomeBannerIndicator, this
            ) { _, _ ->

            }.attach()
        }
    }

    private fun setTestButtonEvent(view: View) {
        view.findViewById<Button>(R.id.btn_move_test_my_pictures).setOnClickListener {
            val intent = Intent(activity, LastingActivity::class.java)
            startActivity(intent)
        }
        // for test usage edit this later
        view.findViewById<Button>(R.id.btn_move_test_my_pictures).visibility = View.INVISIBLE
    }

    private fun setTestButtonEventSecond(view: View) {
        view.findViewById<Button>(R.id.btn_move_test_profile_edit).setOnClickListener {
            val intent = Intent(activity, ProfileAddEditActivity::class.java)
            startActivity(intent)
        }
        // for test usage edit this later
        view.findViewById<Button>(R.id.btn_move_test_profile_edit).visibility = View.INVISIBLE
    }

    private fun setTestButtonEventThird(view: View) {
        view.findViewById<Button>(R.id.btn_move_test_sign_start).setOnClickListener {
            val intent = Intent(activity, SignBeforeStartActivity::class.java)
            startActivity(intent)
        }
        // for test usage edit this later
        view.findViewById<Button>(R.id.btn_move_test_sign_start).visibility = View.INVISIBLE
    }


}