@file:Suppress("unused")

package com.shoppi.app.util

import android.content.Context
import android.net.Uri

/*------ Extension on intent ------*/

//fun Intent?.getFilePath(context: Context): String {
//    return this?.data?.let { data -> RealPathUtil.getRealPath(context, data) ?: "" } ?: ""
//}

fun Uri?.getFilePath(context: Context): String {
    return this?.let { uri -> RealPathUtil.getRealPath(context, uri) ?: "" } ?: ""
}

//fun ClipData.Item?.getFilePath(context: Context): String {
//    return this?.uri?.getFilePath(context) ?: ""
//}
