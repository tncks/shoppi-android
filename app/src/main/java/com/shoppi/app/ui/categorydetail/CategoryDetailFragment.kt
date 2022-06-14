package com.shoppi.app.ui.categorydetail

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.shoppi.app.JustForContApplication
import com.shoppi.app.R
import com.shoppi.app.common.KEY_CATEGORY_PERIOD
import com.shoppi.app.databinding.FragmentCategoryDetailBinding
import com.shoppi.app.ui.schedule.ScheduleDataModel
import com.shoppi.app.model.Schedule
import com.shoppi.app.ui.common.EventObserver
import com.shoppi.app.ui.common.factories.ViewModelFactory
import com.shoppi.app.ui.schedule.ScheduleAdapter
import com.shoppi.app.util.*
import kotlinx.coroutines.*
import java.security.SecureRandom


class CategoryDetailFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: CategoryDetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    private val scheduleDataModel: ScheduleDataModel by lazy {
        val activity = requireNotNull(this.activity) {
            "travelz app 404 access lazy problem in process"
        }
        ViewModelProvider(this, ScheduleDataModel.Factory(activity.application))[ScheduleDataModel::class.java]
    }
    private lateinit var binding: FragmentCategoryDetailBinding
    private lateinit var mymap: GoogleMap
    private var isMapToggleOpen: Boolean
    private lateinit var sca: ScheduleAdapter


    init {
        isMapToggleOpen = getStoredStatusOfToggle()
    }

    private fun getStoredStatusOfToggle(): Boolean {
        val prefs: SharedPreferences =
            JustForContApplication.instance.getSharedPreferences("toggleActiveInactiveStatus", Context.MODE_PRIVATE)
        return prefs.getBoolean("toggleActiveInactiveStatus", true)
    }

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

        if (!isMapToggleOpen) {
            hideMap(1)
        }


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
        }

    }

    override fun onResume() {
        super.onResume()

        val prefs: SharedPreferences =
            JustForContApplication.instance.getSharedPreferences("scData", Context.MODE_PRIVATE)
        val rowId = prefs.getLong("rowid", -1)
        val nameValue = prefs.getString("namekeystr1", "")
        val timeValue = prefs.getString("timekeystr2", "")

        if (!(nameValue.isNullOrBlank() && timeValue.isNullOrBlank())) {

            val editor: SharedPreferences.Editor = prefs.edit()
            editor
                .putLong("rowid", -1)
                .putString("namekeystr1", "")
                .putString("timekeystr2", "")
                .apply()


            val randValue = SecureRandom().nextInt(3) // change value of 3 later or other way not rand way
            Log.d("randValue", randValue.toString())   // 0 or 1 or 2
            val insertingData = Schedule(rowId + randValue.toLong(), nameValue!!, timeValue!!)
            scheduleDataModel.insert(insertingData)
            scheduleDataModel.refreshupdating()

        }
    }


    private fun setToggleMenuInFragment(myMToolbar: Toolbar) {

        myMToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> findNavController().navigate(R.id.action_category_detail_to_schedule_create)
                R.id.menu_delete -> Toast.makeText(requireContext(), "삭제", Toast.LENGTH_SHORT).show()
                R.id.menu_move -> Toast.makeText(requireContext(), "이동", Toast.LENGTH_SHORT).show()
            }
            true
        }

    }


    @Suppress("RedundantNullableReturnType")
    private fun setToolbar() {
        try {
            val emptyCategoryLabelThatIsBlank: String? = ""
            binding.toolbarCategoryDetail.title = emptyCategoryLabelThatIsBlank
        } catch (e: Exception) {
            e.printStackTrace()
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

        val scheduleAdapter = ScheduleAdapter(scheduleDataModel)
        binding.rvScheduleWeek.adapter = scheduleAdapter

        sca = scheduleAdapter

        scheduleDataModel.basicSchedule.observe(viewLifecycleOwner) { bSchedules ->

            scheduleAdapter.submitList(bSchedules)

        }

        scheduleDataModel.openScheduleEvent.observe(viewLifecycleOwner, EventObserver {

            // 1. use bundleOf() and mapping to send data for next activity
            // 2. or intent use with put data and start that
            // 3. on the other hand other way is to use action_category_detail_to_schedule_create
            findNavController().navigate(
                R.id.action_category_detail_to_schedule_write_edit
            )
        })

        scheduleDataModel.getAll().observe(this) { schedules ->

            scheduleAdapter.submitList(schedules)

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


                val prefs: SharedPreferences =
                    JustForContApplication.instance.getSharedPreferences(
                        "toggleActiveInactiveStatus",
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = prefs.edit()
                editor
                    .putBoolean("toggleActiveInactiveStatus", isMapToggleOpen)
                    .apply()


            }

        }
    }


    private fun hideMap(duration: Int = 500) {

        binding.maptoggletxt.rightDrawable(R.drawable.ic_load)
        binding.maptoggletxt.text = getString(R.string.tvtogglemapopposite)
        binding.lll.slideGenie(duration)
        CoroutineScope(Dispatchers.Default).launch {

            delay((duration + 1).toLong())
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


    private fun reDisplayMap(duration: Int = 500) {

        binding.maptoggletxt.rightDrawable(R.drawable.ic_comp)
        binding.maptoggletxt.text = getString(R.string.tvtogglemap)

        val params: ViewGroup.LayoutParams = binding.clc.layoutParams
        params.height = requireContext().resources.getDimensionPixelSize(R.dimen.height_in_dp)
        binding.clc.layoutParams = params


        binding.lll.apply {
            visible(true)
        }

        binding.lll.slideBack(duration)

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

