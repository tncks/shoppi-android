package com.shoppi.app.ui.history


import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shoppi.app.JustForContApplication
import com.shoppi.app.R
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.common.PrepareJsonHelper
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.databinding.ItemHistoryBinding
import com.shoppi.app.model.Category
import com.shoppi.app.network.ApiService
import com.shoppi.app.ui.ProfileAddEditActivity
import com.shoppi.app.ui.category.CategoryBoolLiveArray
import com.shoppi.app.ui.common.CategoryDiffCallback
import com.shoppi.app.util.slideGenie
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class HistoryAdapter(private val viewModel: HistoryViewModel) :
    ListAdapter<Category, HistoryAdapter.HistoryViewHolder>(CategoryDiffCallback()) {


    private var mRecyclerView: RecyclerView? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))

        setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(holder, position)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView

        super.onAttachedToRecyclerView(recyclerView)
    }


    private fun setUpShowPopUpMenuPerEveryItemOnLongClickOneSecond(
        holder: HistoryAdapter.HistoryViewHolder,
        position: Int
    ) {
        val realPosition = getRealPosWithClickedPosOnDisplayedScreen(position)

        holder.itemView.setOnLongClickListener {

            val popup =
                PopupMenu(
                    holder.itemView.context,
                    holder.itemView,
                    Gravity.CENTER
                )
            popup.menuInflater.inflate(R.menu.list_item_popup_history, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val menuIndex = when (item.itemId) {
                    R.id.mItem01 -> 0
                    R.id.mItem02 -> 1
                    else -> 2
                }
                when (menuIndex) {
                    0 -> {

                        try {
                            Toast.makeText(JustForContApplication.instance, "곧 이용 가능", Toast.LENGTH_SHORT).show()
                        } finally {
                            val ccc = false
                            if (ccc) {
                                // if (!mIsInMultiChoiceMode) {
                                val mIntent = Intent(holder.binding.root.context, ProfileAddEditActivity::class.java)
                                mIntent.putExtra("mIndex", realPosition)
                                holder.binding.root.context.startActivity(mIntent)
                                // }
                            }
                        }

                    }
                    1 -> {
                        Toast.makeText(JustForContApplication.instance, "개발중, 먼 미래에 이용 가능", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {

                        changePropertyFromHistoryToPlan(realPosition, it)

                    }
                }
                true
            }
            popup.show()
            true
        }

    }

    private fun getRealPosWithClickedPosOnDisplayedScreen(positionParam: Int): Int {

        var idxTargetingValue = 0
        val fakeIdx = positionParam
        var i = 0

        var matchedCnt = 0
        for (data in CategoryBoolLiveArray.mUpdates) {
            if (data) {
                if (matchedCnt == fakeIdx) {
                    idxTargetingValue = i
                    break
                }
                matchedCnt++
            }
            i++
        }



        return idxTargetingValue
    }

    private fun changePropertyFromHistoryToPlan(realPosition: Int, itParam: View) {
        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)

        val jsonObjectString = PrepareJsonHelper().prepareCategoryPropertyBoolToggleJson(false)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response =
                service.updateCategoryUpdatedProperty(SAFEUID, realPosition.toString(), requestBody)
            withContext(Dispatchers.Main) {
                itParam.apply {
                    slideGenie(500)


                    delay(502L)
                    visibility = View.INVISIBLE
                    visibility = View.GONE
                }
            }

            withContext(Dispatchers.Default) {
                this@HistoryAdapter.viewModel.externalViewReloadCallOnHistoryScreen()
            }

            delay(500L)

        }
    }


    /*-----------------------------------------------------------------*/

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.viewModel = viewModel
            binding.category = category
            binding.executePendingBindings()
        }
    }


}

