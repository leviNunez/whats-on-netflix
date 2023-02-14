package com.whatsonnetflix.data.local


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NetflixContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(toInsert: List<CategoryEntity>) : List<Long>

    @Query("select * from category_table order by id desc")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("select * from category_table where title = :title")
    suspend fun getCategoryByTitle(title: String): CategoryEntity

    @Query("delete from category_table")
    suspend fun clearAllCategories()

}