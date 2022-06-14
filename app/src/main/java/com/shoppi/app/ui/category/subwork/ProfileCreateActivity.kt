@file:Suppress("DuplicatedCode", "RedundantSamConstructor")

package com.shoppi.app.ui.category.subwork

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.shoppi.app.R
import com.shoppi.app.common.*
import com.shoppi.app.common.bitmapcraft.BitmapConvertUtil
import com.shoppi.app.common.filecraft.UploadUtility
import com.shoppi.app.common.jsoncraft.PrepareJsonHelper
import com.shoppi.app.network.ApiService
import com.shoppi.app.otherresource.external.somelandscapes.dataOfGitStatic
import com.shoppi.app.ui.MainActivity
import com.shoppi.app.ui.category.CategoryBoolLiveArray
import com.shoppi.app.ui.common.dialogcomponent.DialogStylingUtil
import com.shoppi.app.util.setTitleColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.security.SecureRandom
import java.util.*

class ProfileCreateActivity : AppCompatActivity() {

    private lateinit var ivProfileThumbnail: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var ediProfileName: EditText
    private lateinit var ediLocationOfProfile: EditText
    private lateinit var ediPeriodOfProfile: EditText
    private lateinit var ediProfileMemo: EditText
    private var providedSampleImageUrl: String? = null
    private var b: Boolean? = null

    /*---------------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_add_edit)


        ivProfileThumbnail = findViewById(R.id.imageView_profile)
        ivBack = findViewById(R.id.iv_unreal_back_simple)
        ediProfileName = findViewById(R.id.et_profile_name)
        ediLocationOfProfile = findViewById(R.id.et_location_of_profile)
        ediPeriodOfProfile = findViewById(R.id.et_period_of_profile)
        ediProfileMemo = findViewById(R.id.et_profile_memo)
        val toolbarProfile = findViewById<Toolbar>(R.id.toolbar_profile)
        b = false

        /*---------------------------------------------*/

        providedSampleImageUrl = dataOfGitStatic[SecureRandom().nextInt(20)]
        Glide.with(this).load(providedSampleImageUrl).into(ivProfileThumbnail)


        /*---------------------------------------------*/

        if (ediProfileName.text.isNotBlank()) {
            toolbarProfile.menu.findItem(R.id.menu_just).setTitleColor(Color.BLACK)
        } else {
            toolbarProfile.menu.findItem(R.id.menu_just).setTitleColor(Color.LTGRAY)
        }

        toolbarProfile.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_just -> {
                    justSubmit()
                }
                else -> {
                    Log.i("error", "error")
                }
            }
            true
        }

        /*--------------------------------------------*/

        ediProfileName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            @Suppress("ReplaceSizeCheckWithIsNotEmpty")
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length != 0) {
                    toolbarProfile.menu.findItem(R.id.menu_just).setTitleColor(Color.BLACK)
                } else {
                    toolbarProfile.menu.findItem(R.id.menu_just).setTitleColor(Color.LTGRAY)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        /*-------------------------------------------*/

        ivBack.setOnClickListener {
            try {
                if (b != null && b == true) {
                    val builder = AlertDialog.Builder(this, R.style.MDialogTheme)
                    fillDialogContents(builder)
                    setDialogLayoutStyleAndShow(builder)
                }

                if (b != null && b == false) {
                    finish()
                }


            } finally {
                // som
            }
        }


        ediPeriodOfProfile.inputType = 0
        ediPeriodOfProfile.setOnFocusChangeListener { _, b ->
            if (b) {
                val calendarConstraintBuilder = CalendarConstraints.Builder()
                calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())

                var dateResultStr: String
                val myRangeBuilder = MaterialDatePicker.Builder.dateRangePicker()
                myRangeBuilder.setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen)
                myRangeBuilder.setCalendarConstraints(calendarConstraintBuilder.build())
                val datePicker = myRangeBuilder.build()

                datePicker.show(supportFragmentManager, "DatePicker")

                datePicker.addOnPositiveButtonClickListener {
                    dateResultStr = datePicker.headerText

                    val date = it
                    val dateString = (DateFormat.format("yy. M. d.", Date(date.first)).toString())
                    val dateStringEnd = DateFormat.format("yy. M. d.", Date(date.second)).toString()
                    dateResultStr = "$dateString ~ $dateStringEnd"

                    ediPeriodOfProfile.setText(dateResultStr)


                }


            }
        }



        ivProfileThumbnail.setOnClickListener {

            b = true
            val intent = Intent(applicationContext, SelectingActivity::class.java)
            startActivity(intent)

        }


    }

    private fun justSubmit() {
        if (ediProfileName.text.toString().isBlank()) {
            return
        } else {
            try {
                val data: List<String> = listOf(
                    ediProfileName.text.toString(),
                    ediLocationOfProfile.text.toString(),
                    ediPeriodOfProfile.text.toString(),
                    ediProfileMemo.text.toString(),
                    providedSampleImageUrl!!
                )
                reviseMethod(
                    data, intent.getIntExtra("mIndex", CategoryBoolLiveArray.mUpdates.size)
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                finishAffinity()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

        }
    }

    override fun onRestart() {
        super.onRestart()

        logicOnRestart()
    }

    private fun logicOnRestart() {

        b?.let {
            if (it) {
                val prefs: SharedPreferences = getSharedPreferences("bitmapStrData", Context.MODE_PRIVATE)
                val myRawStr = prefs.getString("bitmapStrData", "")

                if (!(myRawStr.isNullOrBlank())) {

                    val cBitmapFromStr = BitmapConvertUtil().strToBitmap(myRawStr)
                    this@ProfileCreateActivity.ivProfileThumbnail.setImageBitmap(cBitmapFromStr!!)

                    // after..
                    doRestChoreWithScopeOnIOHandling(cBitmapFromStr)
                }

            }
        }

    }

    private fun doRestChoreWithScopeOnIOHandling(cBitmapFromStr: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            logicOfRestChore(cBitmapFromStr)
        }
    }

    private suspend fun logicOfRestChore(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {

            val nameStartWith = "imgFile"
            val directFile = File.createTempFile(nameStartWith, ".jpg", applicationContext.cacheDir)
            val outputStream = FileOutputStream(directFile)
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream) // 100 값 수정 X !!
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (UploadUtility().uploadFile(directFile)) {
                providedSampleImageUrl = BACK_AZURE_STATIC_WEB_MEDIA_FILE_SERVER_IMAGE_DIR_URI + directFile.name
            } else {
                Log.w("FAIL", "DO REST, FAIL")
            }
        }
    }

    /*---------------------------------------------*/


    override fun onBackPressed() {

        b?.let {
            if (it) {

                val builder = AlertDialog.Builder(this, R.style.MDialogTheme)

                builder.setTitle(
                    "작성 중인 내용이 있습니다." + "\n" +
                            "나가시겠습니까?"
                )
                    .setMessage("지금까지 작성한 내용이 사라집니다.")
                    .setPositiveButton("나가기",
                        DialogInterface.OnClickListener { _, _ ->


                            super.onBackPressed()
                            finish()
                        })
                    .setNegativeButton(
                        "취소",
                        DialogInterface.OnClickListener { _, _ ->

                        })

                setDialogLayoutStyleAndShow(builder)

            } else {
                finish()
            }
        }

    }


    /*---------------------------------------------*/

    private fun fillDialogContents(builder: AlertDialog.Builder) {
        builder.setTitle(
            "작성 중인 내용이 있습니다." + "\n" +
                    "나가시겠습니까?"
        )
            .setMessage("지금까지 작성한 내용이 사라집니다.")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { _, _ ->
                    finish()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { _, _ ->

                })


    }

    private fun setDialogLayoutStyleAndShow(builder: AlertDialog.Builder) {
        DialogStylingUtil().setDialogMarginAndDisplay(builder)

    }

    /*---------------------------------------------*/

    private fun reviseMethod(dataTexts: List<String>, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareJson(dataTexts)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val resultParamStringValue: String = resultParam.toString()
                service.updateCategories(SAFEUID, resultParamStringValue, requestBody)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        providedSampleImageUrl = dataOfGitStatic[SecureRandom().nextInt(20)]
    }


}

