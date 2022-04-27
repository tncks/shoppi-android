package com.shoppi.app.ui

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.R


class PhotosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)


        val gridView = findViewById<GridView>(R.id.gv_folder)
        val nPos = intent.getIntExtra("value", 0)
        val mmIndex = intent.getIntExtra("mIndex", 0)
        val adapter = GridViewAdapter(this, GalleryActivity.alImages, nPos, mmIndex)
        gridView?.adapter = adapter
    }
}