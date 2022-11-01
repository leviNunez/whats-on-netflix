package com.android.course.whatsonnetflix.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.remote.ContentApiStatus
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.utils.ItemBottomSpacer
import com.android.course.whatsonnetflix.utils.formatNetflixContentDetailText
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.progressindicator.LinearProgressIndicator
import timber.log.Timber

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

@BindingAdapter("netflixContentApiStatus")
fun bindStatusToLinearProgressBar(view: LinearProgressIndicator, status: ContentApiStatus?) {
    status?.let {
        when (it) {
            ContentApiStatus.LOADING -> view.visibility =
                View.VISIBLE
            else -> view.visibility = View.GONE
        }
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

@BindingAdapter("showOnlyWhenNull")
fun View.showOnlyWhenNull(data: LiveData<List<NetflixContentPreview>?>) {
    visibility = when (data.value) {
        null -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("showOnlyWhenNotNullOrEmpty")
fun View.showOnlyWhenNotNullOrEmpty(
    data: LiveData<List<NetflixContentPreview>?>,
) {
    visibility = when {
        !data.value.isNullOrEmpty() -> {
            View.VISIBLE
        }
        else -> View.GONE
    }
}

@BindingAdapter("showOnlyWhenEmpty")
fun TextView.showOnlyWhenEmpty(data: LiveData<List<NetflixContentPreview>?>) {
    data.value?.let {
        when {
            it.isEmpty() -> {
                val searchQuery = "\"$text\""
                visibility = View.VISIBLE
                text = context.getString(R.string.search_no_results_found, searchQuery)
            }
            else -> visibility = View.GONE
        }
    }
}

@BindingAdapter("recyclerViewItemSpacer")
fun RecyclerView.bindItemDecorator(spaceBottom: Float) {
    addItemDecoration(ItemBottomSpacer(spaceBottom.toInt()))
}


