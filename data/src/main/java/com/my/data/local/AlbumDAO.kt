package com.my.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.my.domain.Album

@Dao
interface AlbumDAO {

    @Query("SELECT * FROM album")
    fun loadAll(): List<Album>

    @Query("SELECT * FROM album")
    fun loadAlbums(): LiveData<List<Album>>

    @Query("SELECT * FROM album WHERE albumId = :id")
    fun loadAlbumById(id:Long): LiveData<List<Album>>

    @Query("SELECT DISTINCT albumId FROM album")
    fun loadAlbumIds(): LiveData<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Delete
    fun delete(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Album>)

    @Query("DELETE FROM album")
    fun removeAll()

}