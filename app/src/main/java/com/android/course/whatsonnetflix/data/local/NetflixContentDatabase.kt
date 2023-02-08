package com.android.course.whatsonnetflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CategoryEntity::class, SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NetflixContentDatabase : RoomDatabase() {
    abstract fun netflixContentDao(): NetflixContentDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}