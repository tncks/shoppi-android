package com.shoppi.app.ui.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.common.KEY_CATEGORY_LABEL
import com.shoppi.app.databinding.FragmentCategoryDetailBinding
import com.shoppi.app.ui.common.ViewModelFactory

class CategoryDetailFragment : Fragment() {

    private val viewModel: CategoryDetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentCategoryDetailBinding


    /*---------------------------------------------*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        val tb = (binding.root).findViewById<Toolbar>(R.id.toolbar_category_detail)
        val myListener: View.OnClickListener = View.OnClickListener { findNavController().popBackStack() }
        tb?.setNavigationOnClickListener(myListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val myDetailMenu = binding.toolbarCategoryDetail
        setToggleMenuInFragment(myDetailMenu)


        binding.lifecycleOwner = viewLifecycleOwner
        setToolbar()
        setListAdapter()
        // possible later soon, temporarily disabled, but not removed.
    }

    /*---------------------------------------------*/

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
    }
    */

    /*---------------------------------------------*/

    private fun setToggleMenuInFragment(myMToolbar: Toolbar) {

        myMToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> Toast.makeText(requireContext(), "추가", Toast.LENGTH_SHORT).show()
                R.id.menu_delete -> Toast.makeText(requireContext(), "삭제", Toast.LENGTH_SHORT).show()
                R.id.menu_move -> Toast.makeText(requireContext(), "이동", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }

    /*---------------------------------------------*/

    private fun setToolbar() {
        val categoryLabel = requireArguments().getString(KEY_CATEGORY_LABEL)
        binding.toolbarCategoryDetail.title = categoryLabel
    }

    private fun setListAdapter() {
        val topSellingSectionAdapter = CategoryTopSellingSectionAdapter()
        binding.rvCategoryDetail.adapter = topSellingSectionAdapter


        /*---Detect live data from firebase server fetched complete---------------------*/

        viewModel.topSelling.observe(viewLifecycleOwner) { topSelling ->

            topSellingSectionAdapter.submitList(listOf(topSelling))

        }


    }

}


/*---------------------------------------------*/
/*---------------------------------------------*/
/*---------------------------------------------*/
// Reference for studying
// Remove below codes after production stable version made and completed.
// Dummy Code for Dev, only for testing concat adapters, but I'm not using this for production version now
// these codes are for reference for studying, it would be better to not remove this block of lines
/*
private fun setListAdapter(){
//        val titleAdapter = CategorySectionTitleAdapter()
//        val promotionAdapter = CategoryPromotionAdapter()
//        binding.rvCategoryDetail.adapter = ConcatAdapter(topSellingSectionAdapter, titleAdapter, promotionAdapter)
//        viewModel.topSelling.observe(viewLifecycleOwner) { topSelling ->
//
//            topSellingSectionAdapter.submitList(listOf(topSelling))
//
//        }

        // Also

//        viewModel.promotions.observe(viewLifecycleOwner) {
//            val promotionsDotTitle = getTempDummyDataTitle()
//            val promotionsDotItems = getTempDummyDataItems()
//
//            titleAdapter.submitList(listOf(promotionsDotTitle))
//            promotionAdapter.submitList(promotionsDotItems)
//
//        }
}

private fun getTempDummyDataTitle(): Title {

    return Title("추천상품", null)
}

private fun getTempDummyDataItems(): List<Product> {

    return listOf(
        Product(
            null,
            "[19차 리오더] 페이크 레더 숏 테일러 자켓 블랙",
            10,
            90000,
            null,
            "https://user-images.githubusercontent.com/26240553/163524326-0fd93ab3-bb41-4e06-a86f-9f4f419a3f41.png",
            "jacket_1"
        ),
        Product(
            null,
            "캐시미어 100% 터틀넥 스웨터",
            15,
            70000,
            null,
            "https://user-images.githubusercontent.com/26240553/163524328-c5442ce4-631f-48ef-bfdc-2f9df93aced7.png",
            "sweater_1"
        ),
        Product(
            null,
            "반팔 스트라이프 스웨터",
            20,
            30000,
            null,
            "https://user-images.githubusercontent.com/26240553/163524331-0f3aeee9-5f8b-4649-8394-05eb0f13fa45.png",
            "sweater_2"
        ),
        Product(
            null,
            "화이트 스포츠 점퍼",
            20,
            150000,
            null,
            "https://user-images.githubusercontent.com/26240553/163524336-3f23a6ce-2c80-426d-a176-e5bf7342ece6.png",
            "sports_1"
        )
    )
}
*/