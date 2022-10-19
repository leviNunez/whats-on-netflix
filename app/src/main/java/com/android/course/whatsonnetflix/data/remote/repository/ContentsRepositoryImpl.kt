package com.android.course.whatsonnetflix.data.remote.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.course.whatsonnetflix.data.local.database.ContentDao
import com.android.course.whatsonnetflix.data.local.database.asDomainModel
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.asDatabaseModel
import com.android.course.whatsonnetflix.domain.NewContent
import com.android.course.whatsonnetflix.repository.ContentsRepository
import javax.inject.Inject


class ContentsRepositoryImpl @Inject constructor(
    private val api: ContentsApi,
    private val contentDao: ContentDao
) : ContentsRepository {

    override val newContents: LiveData<List<NewContent>> =
        Transformations.map(contentDao.getNewContents()) {
            it.asDomainModel()
        }

    override suspend fun refreshNewContents() {
        val newContents = api.getNewContents()
        contentDao.insertAll(*newContents.asDatabaseModel())
    }

}