package com.android.course.whatsonnetflix.utils

import android.graphics.Rect
import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemBottomSpacer(private val spaceBottom: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = spaceBottom
    }

}

fun RecyclerView.scrollToTopOfList(scope: LifecycleCoroutineScope) {
    scope.launch {
        delay(100)
        this@scrollToTopOfList.scrollToPosition(0)
    }
}