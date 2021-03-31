package com.my.data

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.my.data.local.AlbumDAO
import com.my.data.local.asDomainModel
import com.my.data.remote.ApiInterface
import com.my.data.remote.NetworkAlbum
import com.my.data.remote.asDatabaseModel
import com.my.domain.Album
import com.my.domain.IRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Manage local and remote data
 */
open class Repository(
    private val apiInterface: ApiInterface,
    @VisibleForTesting val dataDAO: AlbumDAO
) :
    IRepository {

    companion object {
        const val TAG = "Repository"
    }

    override suspend fun refreshAlbums() {
        try {
            val response = apiInterface.getAlbums()
            if (response.isSuccessful) {
                insertInDataBase(response.body()!!)
            }
        } catch (e: IOException) {
            Log.w(TAG, "refreshAlbums: response failed: " + e.message)
        }
    }

    @VisibleForTesting
    suspend fun insertInDataBase(albums: List<NetworkAlbum>) {
        withContext(IO) {
            dataDAO.insertAll(albums.asDatabaseModel())
        }
    }

    override fun loadAlbums(): LiveData<List<Album>> {
        return Transformations.map(dataDAO.loadAlbums()) {
            it.asDomainModel()
        }
    }

    override fun loadAlbums(id: Long): LiveData<List<Album>> {
        return Transformations.map(dataDAO.loadAlbumById(id)) {
            it.asDomainModel()
        }
    }

    override fun loadAlbumIds(): LiveData<List<Long>>? {
        return dataDAO.loadAlbumIds()
    }

}