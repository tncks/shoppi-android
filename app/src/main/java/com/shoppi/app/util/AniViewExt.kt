package com.shoppi.app.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import android.view.View
import android.view.animation.ScaleAnimation
import kotlin.math.roundToInt

fun View.slideGenie(duration: Int = 500) {
    visibility = View.VISIBLE

    val animate2 = ScaleAnimation(
        1f,
        1f,
        1f,
        0.001f,
        ScaleAnimation.RELATIVE_TO_PARENT,
        .9f,
        ScaleAnimation.RELATIVE_TO_PARENT,
        .2f
    )

    animate2.duration = duration.toLong()
    animate2.fillAfter = true
    this.startAnimation(animate2)
}

fun View.slideBack(duration: Int = 500) {
    visibility = View.VISIBLE

    val animate2 = ScaleAnimation(
        1f,
        1f,
        0.001f,
        1f,
        ScaleAnimation.RELATIVE_TO_PARENT,
        .9f,
        ScaleAnimation.RELATIVE_TO_PARENT,
        .2f
    )

    animate2.duration = duration.toLong()
    animate2.fillAfter = true
    this.startAnimation(animate2)
}




fun View.visible(animate: Boolean = true) {
    if (animate) {
        animate().alpha(1f).setDuration(1).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
            }
        })
    } else {
        visibility = View.VISIBLE
    }
}


fun View.invisible(animate: Boolean = true) {
    hide(View.GONE, animate)
}


fun View.gone(animate: Boolean = true) {
    hide(View.GONE, animate)
}


@Suppress("unused")
fun View.visibleOrInvisible(show: Boolean, animate: Boolean = true) {
    if (show) visible(animate) else invisible(animate)
}


@Suppress("unused")
fun View.visibleOrGone(show: Boolean, animate: Boolean = true) {
    if (show) visible(animate) else gone(animate)
}


@Suppress("SameParameterValue")
private fun View.hide(hidingStrategy: Int, animate: Boolean = true) {
    if (animate) {
        animate().alpha(0f).setDuration(300).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
            }
        })
    } else {
        visibility = hidingStrategy
    }
}

@Suppress("unused")
fun convertDpToPx(dp: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return dp * (metrics.densityDpi / 160f).roundToInt()
}

@Suppress("unused")
fun convertPxToDp(px: Int): Int {
    val metrics = Resources.getSystem().displayMetrics
    return (px / (metrics.densityDpi / 160f)).roundToInt()
}


// Refer
// slidegenie
// val animate = TranslateAnimation(0f, this.width.toFloat(), 0f, 0f)
//    animate.duration = duration.toLong()
//    animate.fillAfter = true
// slideback
// val animate = TranslateAnimation(0f, this.width.toFloat(), 0f, 0f)
//    animate.duration = duration.toLong()
//    animate.fillAfter = true
// added