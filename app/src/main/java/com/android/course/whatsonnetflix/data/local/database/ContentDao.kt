package com.android.course.whatsonnetflix.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg newContents: DatabaseNewContent)

    @Query("select * from databasenewcontent")
    fun getNewContents(): LiveData<List<DatabaseNewContent>>
}