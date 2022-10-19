package com.android.course.whatsonnetflix.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
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

@BindingAdapter("apiStatusLoading")
fun bindStatusToProgressBar(view: LinearProgressIndicator, status: ContentApiStatus?) {
    status?.let {
        if (it == ContentApiStatus.LOADING) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiStatusError")
fun bindStatusToImageView(view: ImageView, status: ContentApiStatus?) {
    status?.let {
        if (it == ContentApiStatus.ERROR) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}
