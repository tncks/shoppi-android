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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.shoppi.app.R
import com.shoppi.app.common.KEY_CATEGORY_LABEL
import com.shoppi.app.common.KEY_CATEGORY_PERIOD
import com.shoppi.app.databinding.FragmentCategoryDetailBinding
import com.shoppi.app.ui.common.ViewModelFactory
import com.shoppi.app.util.*
import kotlinx.coroutines.*


class CategoryDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: CategoryDetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentCategoryDetailBinding
    private lateinit var mymap: GoogleMap
    private var isMapToggleOpen: Boolean = true


    /*---------------------------------------------*/

    @Suppress("RedundantNullableReturnType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        val tb = (binding.root).findViewById<Toolbar>(R.id.toolbar_category_detail)
        val myListener: View.OnClickListener = View.OnClickListener { findNavController().popBackStack() }
        tb?.setNavigationOnClickListener(myListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {


            val mapFragment = childFragmentManager.findFragmentById(R.id.mymap) as SupportMapFragment
            mapFragment.getMapAsync(this)

            val myDetailMenu = binding.toolbarCategoryDetail
            setToggleMenuInFragment(myDetailMenu)
            binding.lifecycleOwner = viewLifecycleOwner

            setToolbar()
            setListAdapter()
            setScheduleAdapter()
            setToggleGMapEventListener()


        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // later
        }

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
        try {
            val categoryLabel = requireArguments().getString(KEY_CATEGORY_LABEL)
            binding.toolbarCategoryDetail.title = categoryLabel
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            binding.toolbarCategoryDetail.title = ""
        }
    }

    private fun setListAdapter() {
        val topSellingSectionAdapter =
            CategoryTopSellingSectionAdapter(requireArguments().getString(KEY_CATEGORY_PERIOD))
        binding.rvCategoryDetail.adapter = topSellingSectionAdapter


        viewModel.topSelling.observe(viewLifecycleOwner) { topSelling ->

            topSellingSectionAdapter.submitList(listOf(topSelling))

        }

    }

    private fun setScheduleAdapter() {
        val scheduleAdapter = ScheduleAdapter()
        binding.rvScheduleWeek.adapter = scheduleAdapter


        viewModel.basicSchedule.observe(viewLifecycleOwner) { bSchedules ->

            scheduleAdapter.submitList(bSchedules)

        }

    }

    private fun setToggleGMapEventListener() {
        binding.maptoggletxt.setOnClickListener {

            try {
                if (isMapToggleOpen) {
                    hideMap()
                } else {
                    reDisplayMap()
                }
            } finally {
                isMapToggleOpen = !isMapToggleOpen
            }

        }
    }


    private fun hideMap() {

        binding.maptoggletxt.rightDrawable(R.drawable.ic_load)
        binding.maptoggletxt.text = getString(R.string.tvtogglemapopposite)
        binding.lll.slideGenie(500)
        CoroutineScope(Dispatchers.Default).launch {
            delay(501L)
            withContext(Dispatchers.Main) {
                binding.lll.apply {
                    gone(true)
                }
                val params: ViewGroup.LayoutParams = binding.clc.layoutParams
                params.height = ((requireContext().resources.displayMetrics.heightPixels / 4) * 3)
                binding.clc.layoutParams = params

            }

        }

    }


    private fun reDisplayMap() {

        binding.maptoggletxt.rightDrawable(R.drawable.ic_comp)
        binding.maptoggletxt.text = getString(R.string.tvtogglemap)

        val params: ViewGroup.LayoutParams = binding.clc.layoutParams
        params.height = requireContext().resources.getDimensionPixelSize(R.dimen.height_in_dp)
        binding.clc.layoutParams = params


        binding.lll.apply {
            visible(true)
        }

        binding.lll.slideBack(500)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mymap = googleMap.apply {
                val seoul = LatLng(37.564, 127.001)
                addMarker(MarkerOptions().position(seoul).title("서울"))
                setOnMarkerClickListener { marker ->
                    onMarkerClicked(marker)
                    false
                }
                setOnMapClickListener {

                    // Toast.makeText(requireContext(), "map clicked!", Toast.LENGTH_SHORT).show()

                }
                moveCamera(CameraUpdateFactory.newLatLng(seoul))
                animateCamera(CameraUpdateFactory.zoomTo(10f), 1000, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // later
        }
    }

    private fun onMarkerClicked(marker: Marker) {

        Toast.makeText(requireContext(), marker.title, Toast.LENGTH_SHORT).show() // similar "marker clicked!"
        // Toast.makeText(requireContext(), marker.position.longitude.toString(), Toast.LENGTH_SHORT).show()
        // Toast.makeText(requireContext(), marker.position.latitude.toString(), Toast.LENGTH_SHORT).show()

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



*/

