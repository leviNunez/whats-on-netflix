package com.android.course.whatsonnetflix.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan

fun appendNameToValue(name: String, value: String): SpannableString {
    val sb = StringBuilder()
    sb.append(name)
        .append(" ")
        .append(value)
    val spannable = SpannableString(sb)
    val end = name.length
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

fun appendParenthesis(value: String): String {
    val sb = StringBuilder()
    sb.append("(")
        .append(value)
        .append(")")
    return sb.toString()
}