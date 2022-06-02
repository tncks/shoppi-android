package com.shoppi.app.ui.common

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.shoppi.app.JustForContApplication
import com.shoppi.app.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {

        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(JustForContApplication.instance, R.color.shoppi_grey_shimmer))
            .setHighlightColor(ContextCompat.getColor(JustForContApplication.instance, R.color.shoppi_bluegrey))
            .setBaseAlpha(1f)
            .setHighlightAlpha(1f)
            .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        Glide.with(view)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.ic_traran)
            )
            .load(imageUrl)
            .into(view)
    }

}

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {

        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(JustForContApplication.instance, R.color.shoppi_grey_shimmer))
            .setHighlightColor(ContextCompat.getColor(JustForContApplication.instance, R.color.shoppi_bluegrey))
            .setBaseAlpha(1f)
            .setHighlightAlpha(1f)
            .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        Glide.with(view)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.ic_traran)
            )
            .load(imageUrl)
            .circleCrop()
            .into(view)
    }

}