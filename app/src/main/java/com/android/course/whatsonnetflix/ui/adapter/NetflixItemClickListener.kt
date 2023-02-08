package com.android.course.whatsonnetflix.ui.adapter

import com.android.course.whatsonnetflix.domain.NetflixItemModel

class NetflixItemClickListener(val clickListener: (netflixItemModel: NetflixItemModel) -> Unit) {
    fun onClick(content: NetflixItemModel) =
        clickListener(content)
}