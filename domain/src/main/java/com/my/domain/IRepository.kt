package com.my.domain

import androidx.lifecycle.LiveData

interface IRepository {

    suspend fun refreshAlbums()

    fun loadAlbums(): LiveData<List<Album>>

    fun loadAlbums(id: Long): LiveData<List<Album>>

    fun loadAlbumIds(): LiveData<List<Long>>?
}