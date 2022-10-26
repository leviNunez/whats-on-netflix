package com.android.course.whatsonnetflix.utils

import com.android.course.whatsonnetflix.domain.NetflixContentPreview

class NetflixContentPreviewListener(val clickListener: (contentId: Long) -> Unit) {
    fun onClick(content: NetflixContentPreview) = clickListener(content.netflixId)
}