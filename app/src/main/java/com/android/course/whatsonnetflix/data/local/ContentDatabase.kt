package com.android.course.whatsonnetflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseContentPreview::class, DatabaseContent::class], version = 1, exportSchema = false)
abstract class ContentDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}