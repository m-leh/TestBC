package com.my.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDAO {

    @Query("SELECT * FROM albums")
    fun loadAll(): List<DataBaseAlbum>

    @Query("SELECT * FROM albums")
    fun loadAlbums(): LiveData<List<DataBaseAlbum>>

    @Query("SELECT * FROM albums WHERE albumId = :id")
    fun loadAlbumById(id: Long): LiveData<List<DataBaseAlbum>>

    @Query("SELECT DISTINCT albumId FROM albums")
    fun loadAlbumIds(): LiveData<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: DataBaseAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<DataBaseAlbum>)

    @Query("DELETE FROM albums")
    fun removeAll()

}
