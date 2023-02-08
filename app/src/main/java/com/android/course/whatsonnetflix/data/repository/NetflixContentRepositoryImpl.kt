package com.android.course.whatsonnetflix.data.repository

import android.app.Application
import com.android.course.whatsonnetflix.utils.PrefConfig
import com.android.course.whatsonnetflix.R
import com.android.course.whatsonnetflix.data.local.*
import com.android.course.whatsonnetflix.data.remote.ContentsApi
import com.android.course.whatsonnetflix.data.remote.Result
import com.android.course.whatsonnetflix.data.remote.asDatabaseModel
import com.android.course.whatsonnetflix.data.remote.asDomainModel
import com.android.course.whatsonnetflix.domain.*
import com.android.course.whatsonnetflix.domain.repository.NetflixContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NetflixContentRepositoryImpl @Inject constructor(
    private val api: ContentsApi,
    private val app: Application,
    private val netflixContentDao: NetflixContentDao,
    private val prefConfig: PrefConfig
) : NetflixContentRepository {

    override val categories: Flow<List<CategoryModel>> =
        netflixContentDao.getAllCategories().map {
            it.asCategoryModel()
        }

    override suspend fun getAllRegions(): Result<List<RegionModel>> {
        return try {
            val regions = api.getRegions()
            Result.Success(data = regions.asDomainModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun refreshAllCategories(regionCode: String): Result<List<Long>> {
        return try {
            val newContent = api.getNetflixContent(regionCode)
            val expiringContent =
                api.getExpiringNetflixContent(regionCode)
            val allContent =
                newContent.asDatabaseModel() + expiringContent.asDatabaseModel(isExpiring = true)
            val categories = createCategories(items = allContent)
            netflixContentDao.clearAllCategories()
            val inserted = netflixContentDao.insertAllCategories(categories)
            Result.Success(data = inserted)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun createCategories(items: List<NetflixItemEntity>): List<CategoryEntity> {
        val titles = app.resources.getStringArray(R.array.titles).toList()

        return listOf(
            CategoryEntity(
                title = titles[INDEX_NEW],
                netflixItemList = NetflixItemList(items = items.filter { !it.isExpiring }.take(20))
            ),
            CategoryEntity(
                title = titles[INDEX_TV_SHOWS],
                netflixItemList = NetflixItemList(
                    items = items.filter { it.titleType == NetflixItemTitleType.TVSHOWS.value }
                        .shuffled().take(MAX_ITEMS)
                )
            ),
            CategoryEntity(
                title = titles[INDEX_MOVIES],
                netflixItemList = NetflixItemList(items = items.filter { it.titleType == NetflixItemTitleType.MOVIES.value }
                    .shuffled().take(MAX_ITEMS))
            ),
            CategoryEntity(
                title = titles[INDEX_LEAVING_SOON],
                netflixItemList = NetflixItemList(
                    items = items.filter { it.isExpiring }.take(
                        MAX_ITEMS
                    )
                )
            )
        ).reversed()

    }

    companion object {
        const val INDEX_NEW = 0
        const val INDEX_TV_SHOWS = 1
        const val INDEX_MOVIES = 2
        const val INDEX_LEAVING_SOON = 3

        const val MAX_ITEMS = 50
    }

    override suspend fun getCategoryByTitle(title: String): CategoryModel =
        netflixContentDao.getCategoryByTitle(title).asCategoryModel()

    override suspend fun getContentByTitle(title: String): Result<List<NetflixItemModel>> {
        return try {
            val result = api.getNetflixContentByTitle(title)
            Result.Success(data = result.asDomainModel())
        } catch (e: Exception) {
            Result.Error(exception = e)
        }
    }
}