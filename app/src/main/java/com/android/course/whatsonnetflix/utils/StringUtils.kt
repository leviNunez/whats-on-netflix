package com.android.course.whatsonnetflix.utils

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*


fun formatNetflixContentDetailText(title: String, subtitle: String): SpannableString {
    val toFormat = "$title $subtitle"
    val spannable = SpannableString(toFormat)
    val end = title.length
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

fun appendParenthesis(value: String): String {
    return "($value)"
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): Date {
    val c = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val formattedTime = sdf.format(c.time)
    return Date.valueOf(formattedTime)
}

fun String.convertSecondsToTime(): String {
    val temp = this.toLong()
    val hours = temp / 3600
    val minutes = (temp % 3600) / 60
    val seconds = (temp % 3600) % 60;

    val toConvert = "$hours:$minutes:$seconds"
    val time = Time.valueOf(toConvert)

    return time.toString()
}

fun String.convertToDate(): Date {
    return Date.valueOf(this)
}

fun String.decodeHtmlEntities(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}