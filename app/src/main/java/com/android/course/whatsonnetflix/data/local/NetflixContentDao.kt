package com.android.course.whatsonnetflix.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NetflixContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg netflixContentPreview: NetflixContentPreviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetflixContent(content: NetflixContentEntity)

    @Query("select * from content_preview_table where titleType = 'series' order by titleDate desc")
    fun getTvShows(): LiveData<List<NetflixContentPreviewEntity>>

    @Query("select * from content_preview_table where titleType = 'movie' order by titleDate desc")
    fun getMovies(): LiveData<List<NetflixContentPreviewEntity>>

    @Query("select * from content_detail_table where netflixId = :key")
    suspend fun getNetflixContentById(key: Long): NetflixContentEntity?
}