package com.shoppi.app.ui.signstep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.R
import com.shoppi.app.common.GLOBALUID
import com.shoppi.app.common.SAFEUID
import com.shoppi.app.myjetcp.MainComposeActivity


class SignBeforeStartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_before_start)


        findViewById<Button>(R.id.bt_forward_join).setOnClickListener {
            startActivity(Intent(this, JoinNormalNewActivity::class.java))
        }
    }

    @Suppress("DuplicatedCode")
    override fun onResume() {
        super.onResume()


        val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
        GLOBALUID = prefs.getString("UID", "-10")
        if (GLOBALUID == "-10") {
            GLOBALUID = null
        } else {
            SAFEUID = GLOBALUID!!
        }

        if (GLOBALUID != null) {
            val intent = Intent(this, MainComposeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}