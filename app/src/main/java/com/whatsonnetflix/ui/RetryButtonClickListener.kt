package com.whatsonnetflix.ui

class RetryButtonClickListener(val clickListener: () -> Unit) {
    fun onButtonClicked() {
        clickListener()
    }
}