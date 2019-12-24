package com.app2m.githaoa

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl"])
    fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context).load(imageUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(imageView)
    }
}