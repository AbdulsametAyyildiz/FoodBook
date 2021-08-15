package com.kalemlisipahi.foodbook.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kalemlisipahi.foodbook.R


fun ImageView.downloadImage(url:String, placeholder: CircularProgressDrawable)
{
    Glide
        .with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(placeholder).error(R.color.white))
        .load(url)
        .into(this)
}

fun myPlaceHolder(context : Context) : CircularProgressDrawable
{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 3f
        centerRadius = 20f
        start()
    }
}

@BindingAdapter ("android:downloadImage")
fun downloadImageXML(view: ImageView, url: String)
{
    view.downloadImage(url, myPlaceHolder(view.context))
}