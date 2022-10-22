package com.android.course.whatsonnetflix.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.utils.appendNameToValue
import com.android.course.whatsonnetflix.utils.appendParenthesis
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.progressindicator.LinearProgressIndicator


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context).load(imgUri)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image_24dp)
            ).into(imageView)

    }
}


@BindingAdapter("contentDetailString")
fun TextView.setContentDetailString(value: String?) {
    value?.let {
        text = if (value == "series" || value == "movie") {
            appendParenthesis(it)
        } else {
            appendNameToValue(text as String, it)
        }
    }
}



