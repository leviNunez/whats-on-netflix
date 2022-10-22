package com.android.course.whatsonnetflix.utils

import com.android.course.whatsonnetflix.domain.ContentPreview

class ContentPreviewListener(val clickListener: (contentId: Long) -> Unit) {
    fun onClick(content: ContentPreview) = clickListener(content.netflixId)
}