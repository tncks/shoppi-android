package com.shoppi.app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.common.GLOBALUID
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.myjetcp.MainComposeActivity
import com.shoppi.app.ui.signstep.SignBeforeStartActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
        GLOBALUID = prefs.getString("UID", "-10")
        if (GLOBALUID == "-10") {
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