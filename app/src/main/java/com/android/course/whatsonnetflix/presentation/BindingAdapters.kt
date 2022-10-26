package com.android.course.whatsonnetflix.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.utils.formatNetflixContentDetailText
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image_24dp)
        ).into(imageView)

    }
}


@BindingAdapter("contentDetailString")
fun TextView.setNetflixContentDetailText(value: String?) {
    value?.let {
        val title = text.toString()
        val subtitle = if (it == "00:00:00") context.getString(R.string.tv_shows_runtime) else it
        text = formatNetflixContentDetailText(title, subtitle)
    }
}



