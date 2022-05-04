package com.shoppi.app.ui.cart

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.shoppi.app.R
import com.shoppi.app.common.*
import com.shoppi.app.petwork.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.File

class CartFragment : Fragment() {

    private var viewProfile: View? = null
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && validAccessAvailablePermission()) {

                val intent: Intent? = result.data
                val tmpPreview = intent?.data


                if (tmpPreview != null) {
                    getViewProfile()?.findViewById<ImageView>(R.id.imageView)?.setImageURI(tmpPreview)
                    val tmpPreview2: Uri = tmpPreview
                    val nameStartWith = "imgFile"
                    val createdTmpFile: File = TempFileIOUtility().createFileFromUri(
                        context,
                        nameStartWith,
                        tmpPreview2
                    )
                    val fullFileName = createdTmpFile.name
                    UploadUtility2().uploadFile(createdTmpFile)
                    val prePathNameURL = BACK_AZURE_STATIC_WEB_MEDIA_FILE_SERVER_IMAGE_DIR_URI + fullFileName

                    getViewProfile()?.findViewById<EditText>(R.id.plain_text_input2)?.text =
                        Editable.Factory.getInstance().newEditable(prePathNameURL)
                }

            }
        } // End of Register Start For Result

    /*-------------------------------------------------------------------*/


    // Getter of viewProfile
    private fun getViewProfile(): View? {

        return this.viewProfile
    }

    /*-------------------------------------------------------------------*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewProfile = inflater.inflate(R.layout.fragment_cart, container, false)


        return viewProfile
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*-- Step1 setOnClick Event --------------------*/
        setFABClickEvent(view)

        /*-- Step2 setOnClick Event --------------------*/
        val myTestUploadThis = view.findViewById<ImageView>(R.id.imageView)
        myTestUploadThis.setOnClickListener {

            when {

                ContextCompat.checkSelfPermission(
                    requireActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {

                    val choice = arrayOf<CharSequence>("갤러리에서 사진 가져오기", "취소하기")
                    val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(this.context)
                    myAlertDialog.setTitle("선택옵션")
                    myAlertDialog.setItems(choice, DialogInterface.OnClickListener { _, item ->
                        when {
                            choice[item] == "갤러리에서 사진 가져오기" -> {

                                startForResult.launch(
                                    Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                    )
                                )

                            }
                            choice[item] == "취소하기" -> {
                                val canDummy = 0
                                Log.i("dummy", canDummy.toString())

                            }
                        }
                    })
                    myAlertDialog.show()

                }


                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }


                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }

        }

    }

    /*-------------------------------------------------------------------*/

    private fun setFABClickEvent(view: View) {

        val myFlB = view.findViewById<FloatingActionButton>(R.id.my_submit_fbutton)

        myFlB.setOnClickListener {


            val string0: String = (view.findViewById<EditText>(R.id.plain_text_input0)).text.toString()
            val string1: String = (view.findViewById<EditText>(R.id.plain_text_input1)).text.toString()
            val string2: String = (view.findViewById<EditText>(R.id.plain_text_input2)).text.toString()

            if (string0 == "" || string1 == "" || string2 == "") {

                Log.i("dummy", "dummy")

            } else {
                val preferences: SharedPreferences? = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
                val resultI: Int? = preferences?.getInt("pref", 0) // 최초 가져올때 아무것도없을때 저장값이 없으므로 두번째인자 0
                var resultParam = 0

                if (resultI != null) {
                    resultParam = resultI
                }

                putMethod(string0, string1, getUrlPathFromInputIfExistOrGetDefault(string2), resultParam)


                if (resultI != null) {
                    val resultP: Int = resultI

                    preferences.edit {
                        putInt("pref", resultP + 1)
                    }

                }

                Toast.makeText(this.context, "새로운 여행계획이 생성되었어요!", Toast.LENGTH_SHORT).show()
            } // End of else

        }

    }

    private fun getUrlPathFromInputIfExistOrGetDefault(usersInput: String): String {
        val specialTestDataLocationName = "https://agile"
        val originalParam: String = usersInput

        return if (usersInput.substring(0 until 13) == specialTestDataLocationName) {
            originalParam
        } else {
            val defaultIPath =
                "https://user-images.githubusercontent.com/26240553/163684065-9db17013-5ee7-43a8-8dff-a5be66271dd2.jpg"
            defaultIPath
        }
    }

    private fun putMethod(one: String, two: String, three: String, resultParam: Int) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareJson(one, two, three)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            val resultParamStringValue: String = resultParam.toString()

            val response = service.updateCategories(SAFEUID, resultParamStringValue, requestBody)

            withContext(Dispatchers.Main) {
                Log.i("dummy", response.isSuccessful.toString())
            }
        }
    }


    /*-------------------------------------------------------------------*/

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this.context)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    private fun validAccessAvailablePermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )) == PackageManager.PERMISSION_GRANTED
    }

}