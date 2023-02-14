package com.whatsonnetflix.utils

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import java.sql.Date

fun String.convertSecondsToTime(): String {
    val temp = this.toLong()
    val hours = temp / 3600
    val minutes = (temp % 3600) / 60

    return "${hours}h ${minutes}m"
}

fun String.convertToDate(): Date {
    return Date.valueOf(this)
}

fun String.decodeHtmlEntities(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        this
    }
}

fun formatNoResultsText(constant: String, searchTerm: String): SpannableString {
    val toFormat = "$constant $searchTerm"
    val spannable = SpannableString(toFormat)
    val start = constant.length
    val end = spannable.length
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}