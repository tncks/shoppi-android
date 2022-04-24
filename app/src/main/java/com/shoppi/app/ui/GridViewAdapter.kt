package com.shoppi.app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shoppi.app.R

class GridViewAdapter(context: Context, private val al_menu: ArrayList<Model_images>, int_position: Int) :
    ArrayAdapter<Model_images>(context, R.layout.adapter_photosfolder, al_menu) {
    private lateinit var viewHolder: ViewHolder
    private var int_position: Int = 0

    init {
        this.int_position = int_position
    }


    override fun getCount(): Int {
        return al_menu[int_position].getAl_imagepath().size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return if (al_menu[int_position].getAl_imagepath().size > 0) {
            al_menu[int_position].getAl_imagepath().size
        } else {
            1
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView2: View
        if (convertView == null) {

            viewHolder = ViewHolder()

            convertView2 = LayoutInflater.from(context).inflate(R.layout.adapter_photosfolder, parent, false)

            viewHolder.tv_foldern = convertView2.findViewById<TextView>(R.id.tv_folder)
            viewHolder.tv_foldersize = convertView2.findViewById<TextView>(R.id.tv_folder2)
            viewHolder.iv_image = convertView2.findViewById<ImageView>(R.id.iv_image)
            convertView2.tag = viewHolder
        } else {

            viewHolder = convertView.tag as ViewHolder
            viewHolder.tv_foldern?.visibility = View.GONE
            viewHolder.tv_foldersize?.visibility = View.GONE
            Glide.with(context).load("file://" + al_menu[int_position].getAl_imagepath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image!!)
            return convertView
        }
        viewHolder.tv_foldern?.visibility = View.GONE
        viewHolder.tv_foldersize?.visibility = View.GONE
        Glide.with(context).load("file://" + al_menu[int_position].getAl_imagepath().get(position))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(viewHolder.iv_image!!)

        return convertView2
    }

    class ViewHolder {
        var tv_foldern: TextView? = null
        var tv_foldersize: TextView? = null
        var iv_image: ImageView? = null
    }
}