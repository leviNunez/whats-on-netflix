package com.android.course.whatsonnetflix.ui

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.utils.convertSecondsToTime
import com.android.course.whatsonnetflix.utils.formatNoResultsText
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("netflixItemThumbnail")
fun setThumbnailUrl(imageView: ImageView, netflixItemModel: NetflixItemModel?) {
    netflixItemModel?.let {
        val img = if (it.thumbnail != "0") it.thumbnail else it.poster
        val imgUri = img.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.image_place_holder)
                .error(R.drawable.ic_broken_image_24dp)
        ).into(imageView)
    }
}

@BindingAdapter("netflixItemPoster")
fun setPosterUrl(imageView: ImageView, netflixItemModel: NetflixItemModel?) {
    netflixItemModel?.let {
        val img = if (it.poster != "0") it.poster else it.thumbnail
        val imgUri = img.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image_24dp)
        ).into(imageView)
    }
}

@BindingAdapter("netflixItemTitleType")
fun TextView.setNetflixItemType(value: String?) {
    value?.let { titleType ->
        text = titleType.replaceFirstChar { it.uppercase() }
    }
}

@BindingAdapter("netflixItemRuntime")
fun TextView.setNetflixItemRuntime(value: String?) {
    value?.let { runtime ->
        when (runtime) {
            "0" -> visibility = View.GONE
            else -> text = runtime.convertSecondsToTime()
        }
    }
}

@BindingAdapter("progressIndicatorVisibility")
fun View.showWhenLoading(isLoading: Boolean) {
    visibility = when (isLoading) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

@BindingAdapter("frameLayoutVisibility")
fun FrameLayout.showWhenNotLoading(isLoading: Boolean) {
    visibility = when (isLoading) {
        true -> View.GONE
        false -> View.VISIBLE
    }
}

@BindingAdapter("recyclerViewVisibility")
fun RecyclerView.showWhenNotEmpty(data: List<Any>?) {
    data?.let {
        visibility = when {
            data.isNotEmpty() -> View.VISIBLE
            else -> View.GONE
        }
    }
}

@BindingAdapter("viewVisibility")
fun View.toggleVisibility(isVisible: Boolean) {
    visibility = when (isVisible) {
        true -> View.VISIBLE
        false -> View.GONE
    }

}

@BindingAdapter("noResultsTextViewVisibility")
fun TextView.showOnEmptyList(data: List<NetflixItemModel>?) {
    data?.let {
        visibility = when {
            data.isEmpty() -> View.VISIBLE
            else -> View.GONE
        }
    }
}

@BindingAdapter("noResultsText")
fun TextView.setNoResultsText(searchTerm: String) {
    text = formatNoResultsText(context.getString(R.string.search_no_results_found), searchTerm)
}

@BindingAdapter("retryLayoutVisibility")
fun View.showOnError(isVisible: Boolean) {
    visibility = when (isVisible) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}


