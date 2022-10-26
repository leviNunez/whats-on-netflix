package com.android.course.whatsonnetflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [NetflixContentPreviewEntity::class, NetflixContentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NetflixContentDatabase : RoomDatabase() {
    abstract fun contentDao(): NetflixContentDao
}