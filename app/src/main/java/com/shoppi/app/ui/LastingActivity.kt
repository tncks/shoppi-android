package com.shoppi.app.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shoppi.app.R
import com.shoppi.app.common.FIRST_BASE_HEIGHT
import com.shoppi.app.common.MY_HEIGHT_INTERVAL_TOP_BOTTOM


@Suppress("RemoveRedundantQualifierName")
class LastingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lasting)

        /*------------------------------------------------------------*/

        val gridLayoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, true)
        val recycler = findViewById<View>(R.id.recycler) as RecyclerView

        val fab = findViewById<View>(R.id.fbutton) as FloatingActionButton
        fab.visibility = View.INVISIBLE

        recycler.layoutManager = gridLayoutManager
        val adapter = LastingActivity.MyAdapter()
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
        Handler(Looper.getMainLooper()).postDelayed({
            runOnUiThread {
                fab.setOnClickListener {
                    Toast.makeText(this, "Photo Plus", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, GalleryActivity::class.java)
                    startActivity(intent)
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
        }, 1200)
        // End Of Handler

    }

    /*------------------------------------------------------------*/

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

    private class MyAdapter : RecyclerView.Adapter<LastingActivity.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastingActivity.MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView: View = inflater.inflate(R.layout.itemview, parent, false)
            val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.width = parent.width / 3 - layoutParams.leftMargin - layoutParams.rightMargin
            itemView.layoutParams = layoutParams
            return LastingActivity.MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: LastingActivity.MyViewHolder, position: Int) {
            // holder.textView.text = (position).toString()
        }


        /*------------------------------------------------------------*/

        @Suppress("LiftReturnOrAssignment")
        override fun getItemCount(): Int {

            val n = 9
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

        /*------------------------------------------------------------*/

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
        /*------------------------------------------------------------*/

    }


    private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: ImageView

        init {
            textView = itemView.findViewById<View>(R.id.text) as ImageView
        }
    }


}