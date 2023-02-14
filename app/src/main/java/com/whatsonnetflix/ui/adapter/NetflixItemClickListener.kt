package com.whatsonnetflix.ui.adapter

import com.whatsonnetflix.domain.NetflixItemModel

class NetflixItemClickListener(val clickListener: (netflixItemModel: NetflixItemModel) -> Unit) {
    fun onClick(content: NetflixItemModel) =
        clickListener(content)
}