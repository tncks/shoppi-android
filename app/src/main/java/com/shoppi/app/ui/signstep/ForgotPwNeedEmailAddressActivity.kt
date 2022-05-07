package com.shoppi.app.ui.signstep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shoppi.app.R

class ForgotPwNeedEmailAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pw_need_email_address)
    }
}

// Reference for studying
/*
val emailTo = "duddndtncks@gmail.com"  // change this string to server user email value var
val intent = Intent(Intent.ACTION_VIEW)
intent.data = Uri.parse("mailto:$emailTo")
startActivity(intent)
finish()
wrap code with try catch block
 */

// Or Function Style
/*
private fun sendEmail(to: Array<String>) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, to)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }
 */