package com.my.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.my.data.local.AppDatabase
import com.my.data.local.asDomainModel
import com.my.data.remote.RetrofitClient
import com.my.data.remote.asDatabaseModel
import com.my.domain.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl

/**
 * Manage local and remote data
 */
open class Repository(context: Context) {

    // DAO
    private val dataDAO = AppDatabase.getInstance(context)?.dataDao()

    val albums: LiveData<List<Album>> = Transformations.map(dataDAO?.loadAlbums()!!) {
        it?.asDomainModel()
    }

    suspend fun refreshAlbums() {
        withContext(Dispatchers.IO) {
            val albums = RetrofitClient(
                HttpUrl.parse("https://static.leboncoin.fr/")!!
            ).apiInterface.getAlbums()
            dataDAO?.insertAll(albums.asDatabaseModel())
        }
    }

    fun loadAlbums(): LiveData<List<Album>>? {
        return Transformations.map(dataDAO?.loadAlbums()!!) {
            it?.asDomainModel()
        }
    }

    fun loadAlbums(id: Long): LiveData<List<Album>>? {
        return Transformations.map(dataDAO?.loadAlbumById(id)!!) {
            it?.asDomainModel()
        }
    }

    fun loadAlbumIds(): LiveData<List<Long>>? {
        return dataDAO?.loadAlbumIds()
    }

}