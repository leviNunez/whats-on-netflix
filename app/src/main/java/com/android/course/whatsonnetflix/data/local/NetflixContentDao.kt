package com.android.course.whatsonnetflix.data.local


import androidx.lifecycle.LiveData
import androidx.room.*

private const val COL_SERIES = "series"
private const val COL_MOVIE = "movie"

@Dao
interface NetflixContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(netflixContentPreview: List<NetflixContentPreviewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetflixContent(netflixContent: NetflixContentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetflixSearchHistoryItem(netflixSearchHistoryItem: NetflixSearchHistoryEntity)

    @Query("select * from netflix_content_preview_table where titleType = '$COL_SERIES' order by titleDate desc")
    fun getTvShows(): LiveData<List<NetflixContentPreviewEntity>>

    @Query("select * from netflix_content_preview_table where titleType = '$COL_MOVIE' order by titleDate desc")
    fun getMovies(): LiveData<List<NetflixContentPreviewEntity>>

    @Query("select * from netflix_content_detail_table where netflixId = :key")
    suspend fun getNetflixContentById(key: Long): NetflixContentEntity?

    @Query("select * from netflix_search_history_table order by timestamp desc")
    fun getNetflixSearchHistory(): LiveData<List<NetflixSearchHistoryEntity>>

    @Delete
    suspend fun deleteSearchHistoryItem(netflixSearchHistoryItem: NetflixSearchHistoryEntity)

}