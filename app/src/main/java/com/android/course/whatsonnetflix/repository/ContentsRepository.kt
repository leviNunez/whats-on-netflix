package com.android.course.whatsonnetflix.repository

import androidx.lifecycle.LiveData
import com.android.course.whatsonnetflix.domain.NewContent

interface ContentsRepository {
    val newContents: LiveData<List<NewContent>>
    suspend fun refreshNewContents()
}