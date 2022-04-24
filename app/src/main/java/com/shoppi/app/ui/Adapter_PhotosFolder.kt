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

class Adapter_PhotosFolder(context: Context, private val al_menu: ArrayList<Model_images>) :
    ArrayAdapter<Model_images>(context, R.layout.adapter_photosfolder, al_menu) {

    private lateinit var viewHolder: ViewHolder


    override fun getCount(): Int {
        return al_menu.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return if (al_menu.size > 0) {
            al_menu.size
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
            viewHolder.tv_foldern?.text = (al_menu[position].getStr_folder())
            viewHolder.tv_foldersize?.text = (al_menu[position].getAl_imagepath().size).toString()
            Glide.with(context).load("file://" + al_menu[position].getAl_imagepath().get(0))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image!!)
            return convertView
        }
        viewHolder.tv_foldern?.text = (al_menu[position].getStr_folder())
        viewHolder.tv_foldersize?.text = (al_menu[position].getAl_imagepath().size).toString()
        Glide.with(context).load("file://" + al_menu[position].getAl_imagepath().get(0))
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