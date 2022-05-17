package com.shoppi.app.ui

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.shoppi.app.R
import com.shoppi.app.common.BFLAG

class CartEditActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvSubmitFinish: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_edit)
        ivBack = findViewById<ImageView>(R.id.iv_unreal_back_simple)
        tvSubmitFinish = findViewById<TextView>(R.id.tv_simple_complete_edit_submit)


        ivBack.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.MDialogTheme)
            fillDialogContents(builder)
            setDialogLayoutStyleAndShow(builder)
        }

        tvSubmitFinish.setOnClickListener {
            BFLAG = true
            finish()
        }
    }

    private fun fillDialogContents(builder: AlertDialog.Builder) {
        builder.setTitle(
            "작성 중인 내용이 있습니다." + "\n" +
                    "나가시겠습니까?"
        )
            .setMessage("지금까지 작성한 내용이 사라집니다.")
            .setPositiveButton("나가기",
                DialogInterface.OnClickListener { dialog, id ->

                    finish()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    Log.i("dummy", "dummy")
                })
    }

    private fun setDialogLayoutStyleAndShow(builder: AlertDialog.Builder) {
        val aD = builder.show()
        /*--------------------*/
        val btnPositive = aD.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = aD.getButton(AlertDialog.BUTTON_NEGATIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginEnd = 40
        btnNegative.layoutParams = layoutParams
    }
}