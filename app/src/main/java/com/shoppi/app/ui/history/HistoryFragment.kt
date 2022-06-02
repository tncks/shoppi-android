package com.shoppi.app.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.common.KEY_CATEGORY_ID
import com.shoppi.app.common.KEY_CATEGORY_LABEL
import com.shoppi.app.common.KEY_CATEGORY_PERIOD
import com.shoppi.app.databinding.FragmentHistoryBinding
import com.shoppi.app.ui.ProfileCreateActivity
import com.shoppi.app.ui.category.CategoryBoolLiveArray
import com.shoppi.app.ui.common.EventObserver
import com.shoppi.app.ui.common.ViewModelFactory

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myMenu = binding.toolbarCategory
        setToggleMenuInFragment(myMenu)

        binding.lifecycleOwner = viewLifecycleOwner

        historyAdapter = HistoryAdapter(viewModel)
        binding.rvHistoryList.adapter = historyAdapter

        viewModel.items.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }
        viewModel.openHistoryEvent.observe(viewLifecycleOwner, EventObserver {

            openHistoryDetail(it.categoryId, it.label, it.period)

        })

    }


    private fun openHistoryDetail(categoryId: String, categoryLabel: String, categoryPeriod: String) {
        // 아래 코드 삭제
        Toast.makeText(requireContext(), "개발중입니다", Toast.LENGTH_SHORT).show()
        return
        // 위 코드 삭제


        findNavController().navigate(
            R.id.action_history_to_history_detail, bundleOf(
                KEY_CATEGORY_ID to categoryId,
                KEY_CATEGORY_LABEL to categoryLabel,
                KEY_CATEGORY_PERIOD to categoryPeriod
            )
        )


    }


    private fun setToggleMenuInFragment(myMToolbar: Toolbar) {

        myMToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add -> {
                    addingSec()
                }
                R.id.menu_delete -> {
                    Toast.makeText(requireContext(), "개발중", Toast.LENGTH_SHORT).show()
//                    deletingSec(true)
                }
                R.id.menu_move -> {
                    Toast.makeText(requireContext(), "개발중", Toast.LENGTH_SHORT).show()
//                    movingSec()
                }
            }
            true
        }
    }

    private fun addingSec() {
        val smallIntentForFirstProfileCreate = Intent(context, ProfileCreateActivity::class.java)
        smallIntentForFirstProfileCreate.putExtra("mIndex", CategoryBoolLiveArray.mUpdates.size)
        startActivity(smallIntentForFirstProfileCreate)
    }

}