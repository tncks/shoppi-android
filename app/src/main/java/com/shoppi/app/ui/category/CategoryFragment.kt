package com.shoppi.app.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shoppi.app.R
import com.shoppi.app.common.BFLAG
import com.shoppi.app.common.KEY_CATEGORY_ID
import com.shoppi.app.common.KEY_CATEGORY_LABEL
import com.shoppi.app.databinding.FragmentCategoryBinding
import com.shoppi.app.myjetcp.MainComposeActivity
import com.shoppi.app.ui.common.EventObserver
import com.shoppi.app.ui.common.ViewModelFactory
import kotlinx.coroutines.*


class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentCategoryBinding
    private var flag: Boolean = false

    /*---------------------------------------------*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000L)
            if (viewModel.isNothingToShow) {
                withContext(Dispatchers.Main) {
                    binding.showonnothing.visibility = View.VISIBLE
                    binding.showonnothing.setOnClickListener {
                        // 일단은 임시 토스트 생성만, 나중에 다시 변경 -> intent ProfileNewFirstActivity start
                        Toast.makeText(context, "임시 토스트, 코드변경시까지 사용", Toast.LENGTH_SHORT).show()
//                        val smallIntentForFirstProfileAdd = Intent(context, ProfileAddEditActivity::class.java)
//                        smallIntentForFirstProfileAdd.putExtra("mIndex", 0)
//                        startActivity(smallIntentForFirstProfileAdd)
                        // 지금 그대로 액티비티 실행시, 해당 액티비티에서 MalformedException and IException 발생하는 문제 존재
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.showonnothing.visibility = View.GONE
                }

            }
        }

        val categoryAdapter = CategoryAdapter(viewModel)
        binding.rvCategoryList.adapter = categoryAdapter

        viewModel.items.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)

        }

        viewModel.openCategoryEvent.observe(viewLifecycleOwner, EventObserver {
            openCategoryDetail(it.categoryId, it.label)
        })


    }

    /*---------------------------------------------*/


    private fun openCategoryDetail(categoryId: String, categoryLabel: String) {

        findNavController().navigate(
            R.id.action_category_to_category_detail, bundleOf(
                KEY_CATEGORY_ID to categoryId,
                KEY_CATEGORY_LABEL to categoryLabel
            )
        )
    }


    /*-----------------------------------------------------*/


    override fun onResume() {
        super.onResume()

        if (BFLAG) {
            this.flag = true
            BFLAG = false
        }
        if (this.flag == true) {
            this.flag = !this.flag
            val mmIntent = Intent(requireContext(), MainComposeActivity::class.java)
            startActivity(mmIntent)
            activity?.finish()
        }

    }

}


/*-----------------------------------------------------*/
/*-----------------------------------------------------*/
/*-----------------------------------------------------*/
/*
    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }

    // Log.d(TAG, "onCreateView")
    // Log.d(TAG, "ssss submitList()")
    // Log.d(TAG, "onViewCreated")
    // Log.d(TAG, "onResume")

*/

