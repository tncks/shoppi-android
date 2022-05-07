package com.shoppi.app.ui.signstep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shoppi.app.R
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.common.PrepareJsonHelper
import com.shoppi.app.petwork.ApiService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.net.URL

@Suppress("RemoveExplicitTypeArguments")
class JoinNormalNewActivity : AppCompatActivity() {

    private lateinit var ediEmail: EditText
    private lateinit var ediPw: EditText
    private lateinit var ediPwRetype: EditText
    private lateinit var ediNickname: EditText
    private lateinit var submitBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_normal_new)


        ediEmail = findViewById<EditText>(R.id.tv_email_normal_user)
        ediPw = findViewById<EditText>(R.id.tv_pw_normal_user)
        ediPwRetype = findViewById<EditText>(R.id.tv_pw_retype_again)
        ediNickname = findViewById<EditText>(R.id.tv_nickname_normal_user)
        submitBtn = findViewById<Button>(R.id.bt_submit_join_form_btn)

        /*---------------------------------------------------------*/

        // on edit text required all satisfied, then button enabled true set, submitBtn.setEnabled = true
        // if re unsatisfied to require -> then reset again false enabled, lively observe it

        // plus function extra - support password hide and show with imageview onclick, toggle it

        submitBtn.setOnClickListener {
            submitBtn.isEnabled = false
            var checkCode = 0
            if (TextUtils.isEmpty(getEmailText()) || TextUtils.isEmpty(getPwText()) || TextUtils.isEmpty(getPwRetypeText()) || TextUtils.isEmpty(
                    getNicknameText()
                )
            ) {
                checkCode = -1

            }
            if (getPwText() != getPwRetypeText()) {
                checkCode = -2
            }
            // 1. check validation of input data 일단은 입력값 검증은 Skip, 나중에 구현, with validation library or tool
            // 2. request to firebase server with data using retrofit service coroutines IO Scope, without delay
            // 3. work with response, if response is bad, handle it, when is ok then success handle
            // 4. startActivity for next step
            when (checkCode) {
                -1 -> Toast.makeText(applicationContext, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(applicationContext, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                else -> doSome()
            }
            submitBtn.isEnabled = true
        }

        /*---------------------------------------------------------*/


    }

    private fun getEmailText(): String {
        return if (this@JoinNormalNewActivity::ediEmail.isInitialized) {
            this@JoinNormalNewActivity.ediEmail.text.toString()
        } else {
            ""
        }
    }

    private fun getPwText(): String {
        return if (this@JoinNormalNewActivity::ediPw.isInitialized) {
            this@JoinNormalNewActivity.ediPw.text.toString()
        } else {
            ""
        }
    }

    private fun getPwRetypeText(): String {
        return if (this@JoinNormalNewActivity::ediPwRetype.isInitialized) {
            this@JoinNormalNewActivity.ediPwRetype.text.toString()
        } else {
            ""
        }
    }

    private fun getNicknameText(): String {
        return if (this@JoinNormalNewActivity::ediNickname.isInitialized) {
            this@JoinNormalNewActivity.ediNickname.text.toString()
        } else {
            ""
        }
    }

    private fun doSome() {

        val preparedFormData = listOf(getEmailText(), getPwText(), getNicknameText())


        var quickJsonUsersDataForLengthCalculation: String
        var usersDataLength: Int

        lifecycleScope.launch {
            quickJsonUsersDataForLengthCalculation = urlRead()
            usersDataLength = countMatches(quickJsonUsersDataForLengthCalculation)
            delay(500L)
            sendToServer(preparedFormData, usersDataLength.toString())
        }


    }

    private fun countMatches(string: String): Int {
        val pattern = "uid"
        var index = 0
        var count = 0

        while (true) {
            index = string.indexOf(pattern, index)
            index += if (index != -1) {
                count++
                pattern.length
            } else {
                return count
            }
        }
    }

    private fun sendToServer(preparedFormData: List<String>, resultParamStringValue: String) {


        val retrofit = Retrofit.Builder()
            .baseUrl(FIRE_JSON_BASEURL)
            .build()


        val service = retrofit.create(ApiService::class.java)


        val jsonObjectString: String = PrepareJsonHelper().prepareAccountJson(preparedFormData, resultParamStringValue)


        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        CoroutineScope(Dispatchers.IO).launch {

            val response = service.pushOneAccount(resultParamStringValue, requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = prefs.edit()
                    editor.putString("UID", resultParamStringValue)
                    editor.commit()
                    val sucIntent = Intent(applicationContext, JoinCompleteSuccessActivity::class.java)
                    startActivity(sucIntent)
                    finish()
                } else {
                    val failIntent = Intent(applicationContext, JoinIncompleteInvalidorfailActivity::class.java)
                    startActivity(failIntent)
                    finish()
                }
            }
        }
    }

    private suspend fun urlRead() = withContext(Dispatchers.IO) {
        val quickConnect = (FIRE_JSON_BASEURL + "users.json")
        try {
            URL(quickConnect).readText()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}

