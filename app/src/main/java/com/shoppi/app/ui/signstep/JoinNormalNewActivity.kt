@file:Suppress("ReplaceSizeCheckWithIsNotEmpty")

package com.shoppi.app.ui.signstep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shoppi.app.R
import com.shoppi.app.common.FIRE_JSON_BASEURL
import com.shoppi.app.common.PrepareJsonHelper
import com.shoppi.app.network.ApiService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.net.URL
import java.util.regex.Pattern

@Suppress("RemoveExplicitTypeArguments", "RedundantIf", "UnnecessaryVariable")
class JoinNormalNewActivity : AppCompatActivity() {

    private lateinit var ivPrevBackIcon: ImageView
    private lateinit var ediEmail: EditText
    private lateinit var ediPw: EditText
    private lateinit var ediPwRetype: EditText
    private lateinit var ediNickname: EditText
    private lateinit var submitBtn: Button
    private lateinit var flagStr: String
    private lateinit var ivTogglePwVisibility: ImageView
    private lateinit var ivTogglePwRetypeVisibility: ImageView
    private var isPwToggled = false
    private var isPwRetypeToggled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_normal_new)


        ivPrevBackIcon = findViewById<ImageView>(R.id.iv_back_unreal_previous_like_toolbar_second)
        ediEmail = findViewById<EditText>(R.id.tv_email_normal_user)
        ediPw = findViewById<EditText>(R.id.tv_pw_normal_user)
        ediPwRetype = findViewById<EditText>(R.id.tv_pw_retype_again)
        ediNickname = findViewById<EditText>(R.id.tv_nickname_normal_user)
        submitBtn = findViewById<Button>(R.id.bt_submit_join_form_btn)
        ivTogglePwVisibility = findViewById<ImageView>(R.id.iv_hide_eye_pw_normal_user)
        ivTogglePwRetypeVisibility = findViewById<ImageView>(R.id.iv_hide_eye_pw_retype_normal_user)


        /*---------------------------------------------------------*/

        ivPrevBackIcon.setOnClickListener {
            finish()
        }

        /*---------------------------------------------------------*/

        ivTogglePwVisibility.setOnClickListener {
            if (!isPwToggled) {
                ediPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isPwToggled = !isPwToggled
            } else {
                ediPw.transformationMethod = PasswordTransformationMethod.getInstance()
                isPwToggled = !isPwToggled
            }
        }

        ivTogglePwRetypeVisibility.setOnClickListener {
            if (!isPwRetypeToggled) {
                ediPwRetype.transformationMethod = HideReturnsTransformationMethod.getInstance()
                isPwRetypeToggled = !isPwRetypeToggled
            } else {
                ediPwRetype.transformationMethod = PasswordTransformationMethod.getInstance()
                isPwRetypeToggled = !isPwRetypeToggled
            }
        }

        /*---------------------------------------------------------*/

        ediEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length != 0) {
                    val emails = charSequence
                    if (validateInputEmail(emails)) {
                        submitBtn.isEnabled = true
                        flagStr = "0"
                    } else {
                        submitBtn.isEnabled = false
                        flagStr = "error"
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        ediPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length != 0) {
                    val password = ediPw.text.toString()
                    if (validateInput(password)) {
                        submitBtn.isEnabled = true
                        if (this@JoinNormalNewActivity::flagStr.isInitialized && flagStr == "0") {
                            flagStr += "1"
                        }
                    } else {
                        submitBtn.isEnabled = false
                        flagStr = "error"
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        @Suppress("DuplicatedCode")
        ediPwRetype.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length != 0) {
                    submitBtn.isEnabled = true
                } else {
                    submitBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })


        @Suppress("DuplicatedCode")
        ediNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                if (charSequence.length != 0) {
                    submitBtn.isEnabled = true
                } else {
                    submitBtn.isEnabled = false
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        /*---------------------------------------------------------*/


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
            if (!this@JoinNormalNewActivity::flagStr.isInitialized) {
                checkCode = -3
            } else {
                if (flagStr == "error") {
                    checkCode = -6
                } else if (flagStr == "0") {
                    checkCode = -4
                } else if (flagStr != "01") {
                    checkCode = -5
                } else {
                    Log.i("dummy", "dummy")
                }
            }


            when (checkCode) {
                -1 -> Toast.makeText(applicationContext, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
                -2 -> Toast.makeText(applicationContext, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                -3 -> Toast.makeText(applicationContext, "빈 칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                -4 -> Toast.makeText(applicationContext, "입력이 미완료된 빈 칸이 있습니다.", Toast.LENGTH_SHORT).show()
                -5 -> Toast.makeText(applicationContext, "잘못된 입력이 존재합니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                -6 -> Toast.makeText(applicationContext, "올바르지 않은 이메일 주소 혹은 비밀번호 설정입니다.", Toast.LENGTH_SHORT).show()
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

    @Suppress("LiftReturnOrAssignment")
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
                try {
                    val resultIntent: Intent
                    if (response.isSuccessful) {
                        val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = prefs.edit()
                        editor.putString("UID", resultParamStringValue)
                        editor.commit()
                        resultIntent = Intent(applicationContext, JoinCompleteSuccessActivity::class.java)
                    } else {
                        resultIntent = Intent(applicationContext, JoinIncompleteInvalidorfailActivity::class.java)
                    }
                    startActivity(resultIntent)
                } finally {
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

    private fun validateInputEmail(emails: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emails).matches()
    }

    @Suppress("RegExpSimplifiable")
    private fun validateInput(password: String): Boolean {
        try {
            val isSatisfied = mutableListOf<Boolean>()
            val strictPatterns = listOf<Pattern>(
                Pattern.compile("[A-Z]"),
                Pattern.compile("[a-z]"),
                Pattern.compile("[0-9]"),
                Pattern.compile("[!@#$%^&*(){}?]")
            )
            val pwMinLen = 8
            val pwMaxLen = 16

            for (strictPattern in strictPatterns) {
                isSatisfied.add(strictPattern.matcher(password).find())
            }
            isSatisfied.add((password.length in pwMinLen..pwMaxLen))

            val predicate: (Boolean) -> Boolean = { it }
            return isSatisfied.all(predicate)

        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


}

