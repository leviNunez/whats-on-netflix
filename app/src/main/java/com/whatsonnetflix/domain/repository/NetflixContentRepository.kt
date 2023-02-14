package com.whatsonnetflix.domain.repository

import com.whatsonnetflix.data.remote.Result
import com.whatsonnetflix.domain.CategoryModel
import com.whatsonnetflix.domain.NetflixItemModel
import com.whatsonnetflix.domain.RegionModel
import kotlinx.coroutines.flow.Flow

interface NetflixContentRepository {
    val categories: Flow<List<CategoryModel>>

    suspend fun getAllRegions(): Result<List<RegionModel>>

    suspend fun refreshAllCategories(regionCode: String): Result<List<Long>>

    suspend fun getCategoryByTitle(title: String): CategoryModel

    suspend fun getContentByTitle(title: String): Result<List<NetflixItemModel>>
}