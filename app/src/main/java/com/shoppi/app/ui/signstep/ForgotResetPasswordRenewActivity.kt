package com.shoppi.app.ui.signstep

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.R
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.common.PrepareJsonHelper
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.network.ApiService
import com.shoppi.app.util.FormValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class ForgotResetPasswordRenewActivity : AppCompatActivity() {

    private lateinit var subFormButton: Button
    private var newUsersPasswordStr: String? = null
    private var newUsersPasswordRetypeStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_reset_password_renew)


        subFormButton = findViewById(R.id.btn_next_submit_last_to_reset)
        trackUserInputBehaviorAndViewUpdate()
        setSubmitButtonClickEvent(subFormButton)

    }

    /*--------------------------------------------------------------------*/

    private fun trackUserInputBehaviorAndViewUpdate() {

        val tvResetPw = findViewById<EditText>(R.id.tv_resetpw)
        val tvResetPwRetypeAgain = findViewById<EditText>(R.id.tv_resetpw_retype_again)
        trackEdi(tvResetPw)
        trackEdi(tvResetPwRetypeAgain)

    }

    private fun trackEdi(eachOne: EditText?) {
        eachOne?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                findViewById<TextView>(R.id.tv_inputs_do_not_match).visibility = View.GONE
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setSubmitButtonClickEvent(subFormButton: Button?) {
        subFormButton?.setOnClickListener {

            submitProcedure()

        }
    }

    /*--------------------------------------------------------------------*/

    private fun submitProcedure() {
        var checkingCode = 0
        val checkingMessageText = findViewById<TextView>(R.id.tv_inputs_do_not_match)

        newUsersPasswordStr = findViewById<EditText>(R.id.tv_resetpw).text.toString()
        newUsersPasswordRetypeStr = findViewById<EditText>(R.id.tv_resetpw_retype_again).text.toString()

        if (newUsersPasswordStr.isNullOrBlank() || newUsersPasswordRetypeStr.isNullOrBlank()) {
            checkingCode = -2
        } else {
            if (!(FormValidator().validateInput(newUsersPasswordStr!!))) {
                checkingCode = -2
            }
        }

        if (newUsersPasswordStr != newUsersPasswordRetypeStr) {
            checkingCode = -1
        }



        when (checkingCode) {
            -1 -> {
                checkingMessageText.visibility = View.VISIBLE
                // return and close
            }
            -2 -> {
                checkingMessageText.text = "??????????????? ?????? 8??? ??????, ???????????? ??????????????? ?????????."
                checkingMessageText.visibility = View.VISIBLE
                // return and close
            }
            else -> {
                checkingMessageText.visibility = View.GONE

                // IMPORTANT NOTICE
                changeUserPasswordUpdateNewOneDispatch(SAFEUID, newUsersPasswordStr!!)
                // IMPORTANT ????????? SAFEUID??? ???????????? ???????????? ??????, ?????????????????? ?????? ?????? ??????
                // ?????? ????????? ??????????????? ???????????? call?????? ????????? ???
            }
        }
    }


    private fun changeUserPasswordUpdateNewOneDispatch(targetUidToChange: String, changingValueItself: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()
        val service = retrofit.create(ApiService::class.java)
        val jsonObjectString: String = PrepareJsonHelper().preparePasswordChangingJson(changingValueItself)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {

            val response = service.updateUserResetPw(targetUidToChange, requestBody)

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        startActivity(
                            Intent(
                                this@ForgotResetPasswordRenewActivity,
                                SignBeforeStartActivity::class.java
                            )
                        )
                        finish()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
}