package com.android.course.whatsonnetflix.utils

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

fun showToast(context: Context, stringResource: Int) {
    Toast.makeText(context, stringResource, Toast.LENGTH_LONG).show()
}