package com.android.course.whatsonnetflix.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.course.whatsonnetflix.data.local.ContentDao
import com.android.course.whatsonnetflix.data.local.asDomainModel
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.asDatabaseModel
import com.android.course.whatsonnetflix.domain.Content
import com.android.course.whatsonnetflix.domain.ContentPreview
import com.android.course.whatsonnetflix.repository.ContentRepository
import com.android.course.whatsonnetflix.utils.NoDataException
import retrofit2.HttpException

import timber.log.Timber
import javax.inject.Inject


class ContentRepositoryImpl @Inject constructor(
    private val api: ContentsApi,
    private val contentDao: ContentDao,
) : ContentRepository {

    override val tvShows: LiveData<List<ContentPreview>> =
        Transformations.map(contentDao.getTvShows()) {
            it.asDomainModel()
        }

    override val movies: LiveData<List<ContentPreview>>
        get() = TODO("Not yet implemented")

    @Throws(HttpException::class)
    override suspend fun refreshContent() {
        val newContentResponse = api.getNewContent()
        if (newContentResponse.isSuccessful) {
            contentDao.insertAll(*newContentResponse.body()!!.asDatabaseModel())
        } else {
            Timber.i("Here's the error: ${newContentResponse.errorBody()}")
            throw HttpException(newContentResponse)
        }

    }

    @Throws(HttpException::class)
    override suspend fun getContentDetail(contentId: Long) {
        val contentDetailResponse = api.getContentDetail(contentId)
        if (contentDetailResponse.isSuccessful) {
            Timber.i("Content: ${contentDetailResponse.body()}")
            contentDao.insertContent(contentDetailResponse.body()!!.asDatabaseModel())
        } else {
            throw HttpException(contentDetailResponse)
        }

    }

    @Throws(NoDataException::class)
    override suspend fun findContentById(contentId: Long): Content {
        val content = contentDao.getContentById(contentId)
        if (content == null) {
            throw NoDataException("Content does not yet exist on data base")
        } else {
            Timber.i("I went past the exception")
            return content.asDomainModel()
        }
    }
}