package com.android.course.whatsonnetflix.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistoryEntity)

    @Query("select * from search_history_table order by timestamp desc")
    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Query("delete from search_history_table where id = :key")
    suspend fun deleteSearchHistoryById(key: Long)

}
