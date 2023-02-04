package com.example.android01.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import javax.inject.Inject

/**
 * Created by HP on 03.02.2023.
 **/
interface ImageLoader {

    fun loadImage(
        img:ImageView,
        url:String,
    )

    class Base @Inject constructor(): ImageLoader{
        override fun loadImage(img: ImageView, url: String) {
            Glide.with(img)
                .load(url)
                .into(img)
        }

    }
}