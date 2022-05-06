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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shoppi.app.R
import com.shoppi.app.common.*
import com.shoppi.app.model.ModelContents
import com.shoppi.app.model.getAlImageuri
import com.shoppi.app.petwork.ApiService
import com.shoppi.app.repository.category.Supglobal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.File


class GridViewAdapter(context: Context, private val alMenu: ArrayList<ModelContents>, nPos2: Int, mmIndex: Int) :
    ArrayAdapter<ModelContents>(context, R.layout.adapter_photosfolder, alMenu) {
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
        return alMenu[nPos].getAlImageuri().size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return if (alMenu[nPos].getAlImageuri().size > 0) {
            alMenu[nPos].getAlImageuri().size
        } else {
            1
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @Suppress("DuplicatedCode", "RemoveExplicitTypeArguments")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        if (convertView == null) {

            viewHolder = ViewHolder()

            val convertView2 = LayoutInflater.from(context).inflate(R.layout.adapter_photosfolder, parent, false)
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
        Glide.with(context).load(alMenu[nPos].getAlImageuri()[ps])
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(vH.ivImage!!)

    }

    @Suppress("RemoveRedundantQualifierName")
    private fun changeThumbnailAndNavigateToPrevScreen(vH: GridViewAdapter.ViewHolder, ps: Int) {
        vH.ivImage?.setOnClickListener {


            val nameStartWith = "imgFile"
            val createdTmpFile: File = TempFileIOUtility().createFileFromUri(
                context,
                nameStartWith,
                alMenu[nPos2].getAlImageuri()[ps]
            )
            val fullFileName = createdTmpFile.name
            UploadUtility2().uploadFile(createdTmpFile)
            val prePathNameURL = BACK_AZURE_STATIC_WEB_MEDIA_FILE_SERVER_IMAGE_DIR_URI + fullFileName


            reviseMethod(prePathNameURL, mmIndex)

            resetSupG(prePathNameURL)

            // val intent = Intent(context, LastingActivity::class.java)
            // intent.putExtra("prePathNameURL", prePathNameURL)
            // intent.putExtra("mIndex", 0)
            val intent = Intent(context, ProfileAddEditActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)

        }
    }


    @Suppress("DuplicatedCode")
    private fun reviseMethod(thumbPhpFilePath: String, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareSmallJson(thumbPhpFilePath)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            val resultParamStringValue: String = resultParam.toString()

            val response = service.updateItemProfileStyle(SAFEUID, resultParamStringValue, requestBody)

            withContext(Dispatchers.Main) {
                Log.i("dummy", response.isSuccessful.toString())
            }
        }
    }

//    private fun prepareSmallJson(thumbPhpFilePath: String): String {
//        val jsonObject = JSONObject()
//
//        jsonObject.put("thumbnail_image_url", thumbPhpFilePath)
//
//
//        return jsonObject.toString()
//    }

    /* private fun prepareSmallJson(thumbPhpFilePath: List<String>?): String {
          val jsonObject = JSONObject()

          val myMap = listOf<String>("label", "thumbnail_image_url", "location", "period", "memo")
          var i = -1
          for (ls in thumbPhpFilePath!!) {
              i++
              if (ls.isBlank() || ls.isEmpty()) {
                  continue
              }
              jsonObject.put(myMap[i], ls)
          }

          return jsonObject.toString()
      } */


    private fun resetSupG(prePathNameURL: String) {
        val tmpLs = Supglobal.mSup.split(DELIM)
        Supglobal.mSup = PatchHelperUtility().reviseHelperUtil(tmpLs, mmIndex, prePathNameURL)
    }


    class ViewHolder {
        var tvFoldern: TextView? = null
        var tvFoldersize: TextView? = null
        var ivImage: ImageView? = null
    }
}
