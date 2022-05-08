package com.shoppi.app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.common.GLOBALUID
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.common.SP_NOT_FOUND_USER_STRING_CODE
import com.shoppi.app.myjetcp.MainComposeActivity
import com.shoppi.app.ui.signstep.SignBeforeStartActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // no set content view on splash create no layout xml set

        doAutoLoginIfPossibleElseGoSignStartScreen()
    }

    private fun doAutoLoginIfPossibleElseGoSignStartScreen() {

        val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
        GLOBALUID = prefs.getString("UID", SP_NOT_FOUND_USER_STRING_CODE)
        if (GLOBALUID == SP_NOT_FOUND_USER_STRING_CODE) {
            GLOBALUID = null
        } else {
            SAFEUID = GLOBALUID!!
        }

        if (GLOBALUID == null) {
            startActivity(Intent(this, SignBeforeStartActivity::class.java))
        } else {
            startActivity(Intent(this, MainComposeActivity::class.java))
        }

        finish()   // 이 줄은 필수

    }
}