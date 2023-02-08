package com.android.course.whatsonnetflix.domain.repository

import com.android.course.whatsonnetflix.data.remote.Result
import com.android.course.whatsonnetflix.domain.CategoryModel
import com.android.course.whatsonnetflix.domain.NetflixItemModel
import com.android.course.whatsonnetflix.domain.RegionModel
import kotlinx.coroutines.flow.Flow

interface NetflixContentRepository {
    val categories: Flow<List<CategoryModel>>

    suspend fun getAllRegions(): Result<List<RegionModel>>

    suspend fun refreshAllCategories(regionCode: String): Result<List<Long>>

    suspend fun getCategoryByTitle(title: String): CategoryModel

    suspend fun getContentByTitle(title: String): Result<List<NetflixItemModel>>
}