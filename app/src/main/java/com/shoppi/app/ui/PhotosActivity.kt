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
        val int_position = intent.getIntExtra("value", 0)
        val adapter = GridViewAdapter(this, TempReActivity.al_images, int_position)
        gridView?.adapter = adapter
    }
}