package com.android.course.whatsonnetflix.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg newContents: DatabaseContentPreview)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(content: DatabaseContent)

    @Query("select * from content_preview_table where titleType = 'series'")
    fun getTvShows(): LiveData<List<DatabaseContentPreview>>

    @Query("select * from content_preview_table where titleType = 'movies'")
    fun getMovies(): LiveData<List<DatabaseContentPreview>>

    @Query("select * from content_detail_table where netflixId = :key")
    suspend fun getContentById(key: Long): DatabaseContent?
}