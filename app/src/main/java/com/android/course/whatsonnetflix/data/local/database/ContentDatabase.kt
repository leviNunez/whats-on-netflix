package com.android.course.whatsonnetflix.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseNewContent::class], version = 1, exportSchema = false)
abstract class ContentDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}