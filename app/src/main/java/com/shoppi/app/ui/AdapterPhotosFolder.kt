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
import com.shoppi.app.common.FILE_PROTOCOL_PREFIX_STRING
import com.shoppi.app.model.ModelImages
import com.shoppi.app.model.getAlImagepath
import com.shoppi.app.model.getStrFolder

class AdapterPhotosFolder(context: Context, private val alMenu: ArrayList<ModelImages>) :
    ArrayAdapter<ModelImages>(context, R.layout.adapter_photosfolder, alMenu) {

    private lateinit var viewHolder: ViewHolder


    override fun getCount(): Int {
        return alMenu.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return if (alMenu.size > 0) {
            alMenu.size
        } else {
            1
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @Suppress("DuplicatedCode")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView2: View
        if (convertView == null) {

            viewHolder = ViewHolder()

            convertView2 = LayoutInflater.from(context).inflate(R.layout.adapter_photosfolder, parent, false)
            viewHolder.tvFoldern = convertView2.findViewById<TextView>(R.id.tv_folder)
            viewHolder.tvFoldersize = convertView2.findViewById<TextView>(R.id.tv_folder2)
            viewHolder.ivImage = convertView2.findViewById<ImageView>(R.id.iv_image)
            convertView2.tag = viewHolder

            retrieveAllMediaIFiles(viewHolder, position)

            return convertView2
        } else {

            viewHolder = convertView.tag as ViewHolder
            retrieveAllMediaIFiles(viewHolder, position)

            return convertView
        }

    }

    @Suppress("RemoveRedundantQualifierName")
    private fun retrieveAllMediaIFiles(vH: AdapterPhotosFolder.ViewHolder, ps: Int) {

        vH.tvFoldern?.text = (alMenu[ps].getStrFolder())
        vH.tvFoldersize?.text = (alMenu[ps].getAlImagepath().size).toString()
        Glide.with(context).load(FILE_PROTOCOL_PREFIX_STRING + alMenu[ps].getAlImagepath()[0])
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(vH.ivImage!!)
    }

    class ViewHolder {
        var tvFoldern: TextView? = null
        var tvFoldersize: TextView? = null
        var ivImage: ImageView? = null
    }
}