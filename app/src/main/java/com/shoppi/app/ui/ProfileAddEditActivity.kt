package com.shoppi.app.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.shoppi.app.R
import com.shoppi.app.common.*
import com.shoppi.app.petwork.ApiService
import com.shoppi.app.repository.category.Supglobal
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


@Suppress("RemoveExplicitTypeArguments")
class ProfileAddEditActivity : AppCompatActivity() {

    private lateinit var bitmap: Bitmap
    private var preThumbnail: Int = 0
    private lateinit var ivProfileThumbnail: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var tvSubmitFinish: TextView
    private lateinit var originalURL: String
    private lateinit var ediProfileName: EditText
    private lateinit var ediLocationOfProfile: EditText
    private lateinit var ediPeriodOfProfile: EditText
    private lateinit var ediProfileMemo: EditText

    /*---------------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_add_edit)


        ivProfileThumbnail = findViewById<ImageView>(R.id.imageView_profile)
        ivBack = findViewById<ImageView>(R.id.iv_unreal_back_simple)
        tvSubmitFinish = findViewById<TextView>(R.id.tv_simple_complete_edit_submit)
        originalURL = Supglobal.mSup.split(DELIM)[intent.getIntExtra("mIndex", 0)]
        ediProfileName = findViewById<EditText>(R.id.et_profile_name)
        ediLocationOfProfile = findViewById<EditText>(R.id.et_location_of_profile)
        ediPeriodOfProfile = findViewById<EditText>(R.id.et_period_of_profile)
        ediProfileMemo = findViewById<EditText>(R.id.et_profile_memo)

        /*---------------------------------------------*/

        Glide.with(this).load(R.raw.movingcircular).into(ivProfileThumbnail)
        ediProfileName.setText(Supglobal.mLabel.split(DELIM)[intent.getIntExtra("mIndex", 0)])
        ediLocationOfProfile.setText(Supglobal.mLocation.split(DELIM)[intent.getIntExtra("mIndex", 0)])
        ediPeriodOfProfile.setText(Supglobal.mPeriod.split(DELIM)[intent.getIntExtra("mIndex", 0)])
        ediProfileMemo.setText(Supglobal.mMemo.split(DELIM)[intent.getIntExtra("mIndex", 0)])

        /*---------------------------------------------*/

        ivBack.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.MDialogTheme)
            fillDialogContents(builder)
            setDialogLayoutStyleAndShow(builder)
        }

        tvSubmitFinish.setOnClickListener {
            reviseMethod(
                listOf(
                    ediProfileName.text.toString(),
                    ediLocationOfProfile.text.toString(),
                    ediPeriodOfProfile.text.toString(),
                    ediProfileMemo.text.toString()
                ), intent.getIntExtra("mIndex", 0)
            )
            BFLAG = true
            finish()
        }


        ediPeriodOfProfile.inputType = 0
        ediPeriodOfProfile.setOnFocusChangeListener { _, b ->
            if (b) {

                var dateResultStr: String
                val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
                datePicker.show(supportFragmentManager, "DatePicker")

                datePicker.addOnPositiveButtonClickListener {
                    dateResultStr = datePicker.headerText

                    val date = it
                    val dateString = (DateFormat.format("yy. M. d.", Date(date.first)).toString())
                    val dateStringEnd = DateFormat.format("yy. M. d.", Date(date.second)).toString()
                    dateResultStr = "$dateString ~ $dateStringEnd"

                    ediPeriodOfProfile.setText(dateResultStr)
                }

//                datePicker.addOnNegativeButtonClickListener {
//                    Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()
//                }
//
//                datePicker.addOnCancelListener {
//                    Toast.makeText(this, "여행기간 수정이 취소되었습니다.", Toast.LENGTH_LONG).show()
//                }
            }
        }


        /*---------------------------------------------*/

        val mIntent: Intent = intent
        val mPos: Int = mIntent.getIntExtra("mIndex", 0)
        preThumbnail = mPos
        val tmpLs = Supglobal.mSup.split(DELIM)
        ivProfileThumbnail.setOnClickListener {
            val intent = Intent(applicationContext, LastingActivity::class.java)
            intent.putExtra("mIndex", mIntent.getIntExtra("mIndex", 0))
            startActivity(intent)
        }

        /*---------------------------------------------*/
        lifecycleScope.launch {
            delay(1000L)

            calltinOnCreate(tmpLs, mPos)
        }
        /*---------------------------------------------*/

    }


    private suspend fun calltinOnCreate(tmpLs: List<String>, mPos: Int) {
        withContext(Dispatchers.IO) {
            try {
                val iss: InputStream? = connectServerAndGetStreamOfImage(tmpLs[mPos])
                bitmap = BitmapFactory.decodeStream(iss)
            } catch (e: NullPointerException) {
                val iss: InputStream? = connectServerAndGetStreamOfImage(MY_NUPTR_JPG_URL_ON_LOAD_ERROR)
                bitmap = BitmapFactory.decodeStream(iss)
                setBitmapThumbnail()
            } catch (e: FileNotFoundException) {
                setBasicDefaultThumbnail()
            } catch (e: MalformedURLException) {
                setBasicDefaultThumbnail()
            } catch (e: IOException) {
                setBasicDefaultThumbnail()
            } catch (e: Exception) {
                setBasicDefaultThumbnail()
            }
            afterConnection()
        }
    }

    private fun connectServerAndGetStreamOfImage(serverPath: String): InputStream {
        val url = URL(serverPath)
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.doInput = true
        conn.connect()
        return conn.inputStream
    }

    private suspend fun setBitmapThumbnail() {

        withContext(Dispatchers.Main) {
            if (this@ProfileAddEditActivity::bitmap.isInitialized) {
                ivProfileThumbnail.setImageBitmap(bitmap)
            }
        }

    }

    private suspend fun setBasicDefaultThumbnail() {

        withContext(Dispatchers.Main) {
            ivProfileThumbnail.setImageResource(R.drawable.ic_placeholder_add)
        }

    }

    private suspend fun setRefreshTouchThumbnail() {

        withContext(Dispatchers.Main) {
            ivProfileThumbnail.setImageResource(R.drawable.ic_refresh)
        }

    }

    private fun setBasicDefaultThumbnailWithoutContext() {

        ivProfileThumbnail.setImageResource(R.drawable.ic_placeholder_add)

    }

    private suspend fun afterConnection() {

        withContext(Dispatchers.Main) {
            try {
                if (this@ProfileAddEditActivity::bitmap.isInitialized) {
                    ivProfileThumbnail.setImageBitmap(bitmap)
                } else {
                    throw InterruptedException()
                }
            } catch (e: InterruptedException) {
                setBasicDefaultThumbnailWithoutContext()
            } catch (e: Exception) {
                setBasicDefaultThumbnailWithoutContext()
            }
        }

    }

    /*---------------------------------------------*/


    override fun onRestart() {
        super.onRestart()


        // Do not remove this one line code for stability
        Glide.with(this).load(R.raw.movingcircular).into(ivProfileThumbnail)

        /*---------------------------------------------*/
        lifecycleScope.launch {
            delay(2000L)

            // Do not remove four lines code for stability
            withContext(Dispatchers.Main) {
                Glide.with(applicationContext).load(R.raw.movingcircular)
                    .into(ivProfileThumbnail)
            }

            calltinOnRestart()
        }
        /*---------------------------------------------*/

    }

    private suspend fun calltinOnRestart() {
        withContext(Dispatchers.IO) {
            try {
                val iss: InputStream? = connectServerAndGetStreamOfImage(Supglobal.mSup.split(DELIM)[preThumbnail])
                bitmap = BitmapFactory.decodeStream(iss)
            } catch (e: NullPointerException) {
                val iss: InputStream? = connectServerAndGetStreamOfImage(MY_NUPTR_JPG_URL_ON_LOAD_ERROR)
                bitmap = BitmapFactory.decodeStream(iss)
                setBitmapThumbnail()
            } catch (e: MalformedURLException) {
                setBasicDefaultThumbnail()
            } catch (e: FileNotFoundException) {
                setRefreshTouchThumbnail()
            } catch (e: IOException) {
                setBasicDefaultThumbnail()
            } catch (e: Exception) {
                setRefreshTouchThumbnail()
            }
            afterConnection()
        }
    }

    override fun onPause() {
        super.onPause()


        Glide.with(this).load(R.raw.movingcircular).into(ivProfileThumbnail)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.MDialogTheme)
        // Do not change below code block with function call fillDialogContents
        builder.setTitle(
            "작성 중인 내용이 있습니다." + "\n" +
                    "나가시겠습니까?"
        )
            .setMessage("지금까지 작성한 내용이 사라집니다.")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { dialog, id ->
                    if (this@ProfileAddEditActivity::originalURL.isInitialized) {
                        reviseMethod(originalURL, intent.getIntExtra("mIndex", 0))
                        resetSupG(originalURL, intent.getIntExtra("mIndex", 0))
                    }
                    super.onBackPressed()
                    finish()
                })
            .setNegativeButton(
                "취소",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i("dummy", "dummy")
                })

        setDialogLayoutStyleAndShow(builder)
    }

    override fun onResume() {
        super.onResume()


        BFLAG = false
    }

    /*---------------------------------------------*/

    private fun fillDialogContents(builder: AlertDialog.Builder) {
        builder.setTitle(
            "작성 중인 내용이 있습니다." + "\n" +
                    "나가시겠습니까?"
        )
            .setMessage("지금까지 작성한 내용이 사라집니다.")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { dialog, id ->
                    if (this@ProfileAddEditActivity::originalURL.isInitialized) {
                        reviseMethod(originalURL, intent.getIntExtra("mIndex", 0))
                        resetSupG(originalURL, intent.getIntExtra("mIndex", 0))
                    }
                    finish()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i("dummy", "dummy")
                })
    }

    private fun setDialogLayoutStyleAndShow(builder: AlertDialog.Builder) {
        val aD = builder.show()
        /*--------------------*/
        val btnPositive = aD.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = aD.getButton(AlertDialog.BUTTON_NEGATIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginEnd = 40
        btnNegative.layoutParams = layoutParams
    }

    /*---------------------------------------------*/

    private fun reviseMethod(dataTexts: List<String>, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareFlexibleJson(dataTexts)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val resultParamStringValue: String = resultParam.toString()
                service.updateItemProfileStyle(SAFEUID, resultParamStringValue, requestBody)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }


    private fun reviseMethod(FilePath: String, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareFlexibleJson(FilePath)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resultParamStringValue: String = resultParam.toString()
                service.updateItemProfileStyle(SAFEUID, resultParamStringValue, requestBody)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun resetSupG(URL: String, index: Int) {
        val tmpData = Supglobal.mSup.split(DELIM)
        Supglobal.mSup = PatchHelperUtility().reviseHelperUtil(tmpData, index, URL)
    }


}