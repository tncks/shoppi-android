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

class GridViewAdapter(context: Context, private val alMenu: ArrayList<ModelImages>, nPos: Int) :
    ArrayAdapter<ModelImages>(context, R.layout.adapter_photosfolder, alMenu) {
    private lateinit var viewHolder: ViewHolder
    private var nPos: Int = 0

    init {
        this.nPos = nPos
    }


    override fun getCount(): Int {
        return alMenu[nPos].getAlImagepath().size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return if (alMenu[nPos].getAlImagepath().size > 0) {
            alMenu[nPos].getAlImagepath().size
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
        } else {

            viewHolder = convertView.tag as ViewHolder
            retrieveAllMediaIFiles(viewHolder, position)

            return convertView
        }
        retrieveAllMediaIFiles(viewHolder, position)


        return convertView2
    }

    @Suppress("RemoveRedundantQualifierName")
    private fun retrieveAllMediaIFiles(vH: GridViewAdapter.ViewHolder, ps: Int) {

        vH.tvFoldern?.visibility = View.GONE
        vH.tvFoldersize?.visibility = View.GONE
        Glide.with(context).load(FILE_PROTOCOL_PREFIX_STRING + alMenu[nPos].getAlImagepath()[ps])
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