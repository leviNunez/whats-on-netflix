package com.android.course.whatsonnetflix.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.course.whatsonnetflix.data.local.NetflixContentDao
import com.android.course.whatsonnetflix.data.local.asDomainModel
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.asDatabaseModel
import com.android.course.whatsonnetflix.data.remote.asDomainModel
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import com.android.course.whatsonnetflix.domain.NetflixSearchHistoryItem
import com.android.course.whatsonnetflix.domain.asDatabaseModel
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import com.android.course.whatsonnetflix.utils.NoDataException
import retrofit2.HttpException

import timber.log.Timber
import javax.inject.Inject


class NetflixContentRepositoryImpl @Inject constructor(
    private val api: ContentsApi,
    private val netflixContentDao: NetflixContentDao,
) : NetflixContentRepository {

    override val tvShows: LiveData<List<NetflixContentPreview>> =
        Transformations.map(netflixContentDao.getTvShows()) {
            it.asDomainModel()
        }

    override val movies: LiveData<List<NetflixContentPreview>> =
        Transformations.map(netflixContentDao.getMovies()) {
            it.asDomainModel()
        }

    override val searchHistory: LiveData<List<NetflixSearchHistoryItem>> =
        Transformations.map(netflixContentDao.getNetflixSearchHistory()) {
            Timber.i("$it")
            it.asDomainModel()
        }


    @Throws(HttpException::class)
    override suspend fun refreshNetflixContent(type: String) {
        val response = api.getNetflixContent(type)
        if (response.isSuccessful) {
            response.body()?.let {
                netflixContentDao.insertAll(*it.asDatabaseModel())
            }
        } else {
            throw HttpException(response)
        }

    }

    @Throws(HttpException::class)
    override suspend fun getNetflixContentDetail(contentId: Long) {
        val response = api.getNetflixContentDetail(contentId)
        if (response.isSuccessful) {
            response.body()?.let {
                netflixContentDao.insertNetflixContent(it.asDatabaseModel())
            }
        } else {
            throw HttpException(response)
        }

    }

    @Throws(HttpException::class)
    override suspend fun getNetflixContentByTitle(contentTitle: String)
            : List<NetflixContentPreview> {
        Timber.i("Making HTTP call...")
        val response = api.getNetflixContentByTitle(contentTitle)
        if (response.isSuccessful) {
            Timber.i("HTTP call was successful. The title was $contentTitle and the response body is ${response.body()}")
            response.body()?.let {
                return it.asDomainModel()
            }
        }
        throw HttpException(response)
    }

    @Throws(NoDataException::class)
    override suspend fun findNetflixContentById(contentId: Long): NetflixContent {
        val netflixContent = netflixContentDao.getNetflixContentById(contentId)
        if (netflixContent == null) {
            throw NoDataException("NetflixContent does not yet exist on data base")
        } else {
            return netflixContent.asDomainModel()
        }
    }

    override suspend fun addNetflixSearchHistoryItem(searchHistoryItem: NetflixSearchHistoryItem) {
        netflixContentDao.insertNetflixSearchHistoryItem(searchHistoryItem.asDatabaseModel())
    }

    override suspend fun deleteSearchHistoryItem(searchHistoryItem: NetflixSearchHistoryItem) {
        netflixContentDao.deleteSearchHistoryItem(searchHistoryItem.asDatabaseModel())
    }
}