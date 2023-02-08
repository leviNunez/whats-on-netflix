package com.android.course.whatsonnetflix.ui

class RetryButtonClickListener(val clickListener: () -> Unit) {
    fun onButtonClicked() {
        clickListener()
    }
}