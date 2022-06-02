package com.shoppi.app.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shoppi.app.R
import com.shoppi.app.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SelectingActivity : AppCompatActivity() {


    private var alImages3: ArrayList<String> = ArrayList()


    @Suppress("DuplicatedCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lasting)


        setMyAlImagesOnInit()


        /*------------------------------------------------------------*/

        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, true)
        val recycler = findViewById<View>(R.id.recycler) as RecyclerView

        val fab = findViewById<View>(R.id.fbutton) as FloatingActionButton
        fab.alpha = .5f
        fab.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.shoppi_grey_11))
        fab.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.shoppi_grey_11))
        fab.visibility = View.INVISIBLE

        recycler.layoutManager = gridLayoutManager
        @Suppress("RemoveRedundantQualifierName")
        val adapter = SelectingActivity.MyAdapter(this@SelectingActivity.alImages3, applicationContext)
        recycler.adapter = adapter

        /*------------------------------------------------------------*/

        if (adapter.itemCount <= 14) {
            fab.visibility = View.VISIBLE
            val params: ViewGroup.LayoutParams = recycler.layoutParams
            val myHeightStackLevel = determineStackLevelWithItemCount(adapter.itemCount)
            params.height = FIRST_BASE_HEIGHT + (myHeightStackLevel * MY_HEIGHT_INTERVAL_TOP_BOTTOM)
            recycler.layoutParams = params
        } else {
            val params: ViewGroup.LayoutParams = recycler.layoutParams
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            recycler.layoutParams = params
        }

        /*------------------------------------------------------------*/

        // Begin Of Handler
        lifecycleScope.launch {
            delay(10L)

            innScrollSmoothTopLogic(fab, adapter, recycler)

        }
        // End Of Handler
    }


    @Suppress("RemoveRedundantQualifierName", "DuplicatedCode")
    private suspend fun innScrollSmoothTopLogic(
        fab: FloatingActionButton,
        adapter: SelectingActivity.MyAdapter,
        recycler: RecyclerView
    ) {
        withContext(Dispatchers.Main) {
            fab.setOnClickListener {
                Toast.makeText(applicationContext, "이 버튼은 아직 이용하실 수 없습니다. 우선, 아무 이미지를 선택해 보세요!", Toast.LENGTH_LONG)
                    .show()
            }
            if (adapter.itemCount > 14) {
                recycler.smoothScrollToPosition(adapter.itemCount - 1)
            } else {
                fab.visibility = View.VISIBLE
            }

            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && fab.isShown) {
                        fab.hide()
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        fab.show()
                    }

                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }


    private fun setMyAlImagesOnInit() {
        this.alImages3.clear()



        for (idx in 0 until 20) {

            this.alImages3.add(dataOfGitStatic[idx])

        }


    }


    /*------------------------------------------------------------*/

    @Suppress("DuplicatedCode")
    private fun determineStackLevelWithItemCount(itemCnt: Int): Int {
        return if (itemCnt <= (3 * 1)) {
            0
        } else if (itemCnt <= (3 * 2)) {
            1
        } else if (itemCnt <= (3 * 3)) {
            2
        } else if (itemCnt <= (3 * 4)) {
            3
        } else if (itemCnt <= 14) {
            4
        } else {
            4
        }
    }

    /*------------------------------------------------------------*/

    @Suppress("RemoveRedundantQualifierName")
    private class MyAdapter(
        private val alImages3: ArrayList<String>,
        private val aContext: Context
    ) :
        RecyclerView.Adapter<SelectingActivity.MyViewHolder>() {

        private var shimmer: Shimmer


        var shimmerDrawable: ShimmerDrawable

        init {
            this.shimmer = Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(aContext, R.color.shoppi_grey_shimmer))
                .setHighlightColor(ContextCompat.getColor(aContext, R.color.shoppi_bluegrey))
                .setBaseAlpha(1f)
                .setHighlightAlpha(1f)
                .build()


            this.shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }
        }


        @Suppress("DuplicatedCode")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectingActivity.MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView: View = inflater.inflate(R.layout.item_view, parent, false)
            val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = parent.width / 3 - layoutParams.leftMargin - layoutParams.rightMargin
            itemView.layoutParams = layoutParams

            return SelectingActivity.MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: SelectingActivity.MyViewHolder, position: Int) {

            shimmer = Shimmer.ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(aContext, R.color.shoppi_grey_shimmer))
                .setHighlightColor(ContextCompat.getColor(aContext, R.color.shoppi_bluegrey))
                .setBaseAlpha(1f)
                .setHighlightAlpha(1f)
                .build()

            shimmerDrawable = ShimmerDrawable().apply {
                setShimmer(shimmer)
            }


            var sampleImgEachItemUrlPath = ""

            try {

                if (itemCount - position >= 3) {

                    sampleImgEachItemUrlPath = dataOfGitStatic[position]


                } else {

                    sampleImgEachItemUrlPath = if (itemCount - position == 1) {

                        dataOfOtherStatic[0]

                    } else {

                        dataOfOtherStatic[1]

                    }


                }


            } finally {

                Glide.with(holder.itemView.context).load(sampleImgEachItemUrlPath)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(false)
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.sampleportrait)
                    .into(holder.photoView)

            }


        }


        @Suppress("LiftReturnOrAssignment")
        override fun getItemCount(): Int {


            val n = this@MyAdapter.alImages3.size


            if (n <= 14) {
                return if (checkEmptyOrOnlyOneOrTwo(n)) {
                    matchIsEmptyOrOnlyOneOrTwoResult(n)
                } else {
                    addSampleImageForDummyGridNaturalLayout(n)
                }
            } else {
                return addSampleImageForDummyGridNaturalLayout(n)
            }
        }


        private fun checkEmptyOrOnlyOneOrTwo(n: Int): Boolean {
            return checkEmptyOrOnlyOneOrTwoInternalCalculate(n)
        }

        private fun checkEmptyOrOnlyOneOrTwoInternalCalculate(n: Int): Boolean {
            return when (n) {
                0 -> true
                1 -> true
                else -> n == 2
            }
        }


        private fun matchIsEmptyOrOnlyOneOrTwoResult(n: Int): Int {
            return if (n == 1) {
                (n + 1)
            } else {
                n
            }
        }


        private fun addSampleImageForDummyGridNaturalLayout(n: Int): Int {
            if (n % 3 == 0) {
                return (n + 2)
            }
            if ((n - 1) % 3 == 0) {
                return (n + 1)
            }
            return n
        }


    }


    private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photoView: ImageView

        init {
            photoView = itemView.findViewById(R.id.photo)
            connectEachItemSetReactionListener(photoView)

        }

        fun connectEachItemSetReactionListener(photoView: ImageView) {

            photoView.setOnClickListener {

                val bitmap: Bitmap = (it as ImageView).drawable.toBitmap()
                val cStr: String = BitmapConvertUtil().bitmapToStr(bitmap)
                val prefs: SharedPreferences =
                    photoView.context.applicationContext.getSharedPreferences("bitmapStrData", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = prefs.edit()
                editor.putString("bitmapStrData", cStr)
                editor.commit()

                ((it.context) as SelectingActivity).finish()
            }

        }


    }


}