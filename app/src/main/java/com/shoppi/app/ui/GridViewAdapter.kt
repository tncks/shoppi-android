package com.shoppi.app.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shoppi.app.R
import com.shoppi.app.common.FILE_PROTOCOL_PREFIX_STRING
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.common.UploadUtility2
import com.shoppi.app.model.ModelImages
import com.shoppi.app.model.getAlImagepath
import com.shoppi.app.petwork.ApiService
import com.shoppi.app.repository.category.Supglobal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.commons.io.FileUtils
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.File


class GridViewAdapter(context: Context, private val alMenu: ArrayList<ModelImages>, nPos2: Int, mmIndex: Int) :
    ArrayAdapter<ModelImages>(context, R.layout.adapter_photosfolder, alMenu) {
    private lateinit var viewHolder: ViewHolder
    private var nPos: Int = 0
    private val nPos2: Int
    private val mmIndex: Int

    init {
        this.nPos = nPos2
        this.nPos2 = nPos2
        this.mmIndex = mmIndex
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

            retrieveAllMediaIFiles(viewHolder, position)


            changeThumbnailAndNavigateToPrevScreen(viewHolder, position)


            return convertView2
        } else {

            viewHolder = convertView.tag as ViewHolder
            retrieveAllMediaIFiles(viewHolder, position)


            changeThumbnailAndNavigateToPrevScreen(viewHolder, position)


            return convertView
        }

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

    @Suppress("RemoveRedundantQualifierName")
    private fun changeThumbnailAndNavigateToPrevScreen(vH: GridViewAdapter.ViewHolder, ps: Int) {
        vH.ivImage?.setOnClickListener {
            Toast.makeText(context, ps.toString(), Toast.LENGTH_SHORT).show()


            val nameStartWith = "imgFile"
            val createdTmpFile: File = createFileFromUri2(
                nameStartWith,
                alMenu[nPos2].getAlImagepath()[ps]
            )
            val fullFileName = createdTmpFile.name
            UploadUtility2().uploadFile(createdTmpFile)
            val prePathNameURL = "https://agile-savannah-32015.herokuapp.com/images/$fullFileName"


            // 0 => firebase json PATCH only thumbnail value
            reviseMethod(prePathNameURL, mmIndex)

            // 1 => data 처리 with sup global
            val delim = " "
            val tmpLs = Supglobal.mSup.split(delim)
            var revisedString: String = ""
            for (i in 0 until mmIndex) {
                revisedString += tmpLs[i]
                revisedString += " "
            }
            revisedString += prePathNameURL
            revisedString += " "
            for (i in mmIndex + 1 until tmpLs.size) {
                revisedString += tmpLs[i]
                revisedString += " "
            }
            revisedString.dropLast(1)
            Supglobal.mSup = revisedString

            // 2 => Back to Prev Screen here code write ex, finish activity or navigate
            val intent = Intent(context, ProfileAddEditActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)

        }
    }

    private fun createFileFromUri2(name: String, absolutePath: String): File {

        val stream = File(absolutePath).inputStream()
        val formatSuffix = ".jpg"  // only support jpg format
        val file = File.createTempFile(name, formatSuffix, context.cacheDir)
        FileUtils.copyInputStreamToFile(stream, file)

        return file

    }

    private fun reviseMethod(one: String, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = prepareSmallJson(one)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            val resultParamStringValue: String = resultParam.toString()

            val response = service.updateItemProfileStyle(resultParamStringValue, requestBody)

            withContext(Dispatchers.Main) {
                Log.d("glo", response.isSuccessful.toString())
            }
        }
    }

    private fun prepareSmallJson(one: String): String {
        val jsonObject = JSONObject()

        jsonObject.put("thumbnail_image_url", one)


        return jsonObject.toString()
    }


    class ViewHolder {
        var tvFoldern: TextView? = null
        var tvFoldersize: TextView? = null
        var ivImage: ImageView? = null
    }
}
