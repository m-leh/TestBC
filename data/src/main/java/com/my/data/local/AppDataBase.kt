package com.my.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataBaseAlbum::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): AlbumDAO
}