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

    override fun onResume() {
        super.onResume()

        // 만약, shared prefence에서 가져온 값이 있고, uid 검증에 문제가 없으면 자동 로그인 실행, 없으면 아무것도 안함
        // 자동 로그인 실행하면서 GLOBALUID 값을 SP에서 로드한 값으로 세팅

        val prefs: SharedPreferences = getSharedPreferences("UID", Context.MODE_PRIVATE)
        GLOBALUID = prefs.getString("UID", "-10")
        if (GLOBALUID == "-10") {
            GLOBALUID = null
        } else {
            SAFEUID = GLOBALUID!!
        }

        if (GLOBALUID != null) {
            // Toast.makeText(this, "보안 모듈이 작동 중입니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainComposeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}