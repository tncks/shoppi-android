package com.shoppi.app.ui.common.dialogcomponent

import android.app.AlertDialog
import android.widget.LinearLayout

@Suppress("DuplicatedCode")
class DialogStylingUtil {
    fun setDialogMarginAndDisplay(builder: AlertDialog.Builder) {
        val aD = builder.show()
        /*--------------------*/
        val btnPositive = aD.getButton(AlertDialog.BUTTON_POSITIVE)
        val btnNegative = aD.getButton(AlertDialog.BUTTON_NEGATIVE)
        val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
        layoutParams.marginEnd = 40
        btnNegative.layoutParams = layoutParams
    }
}