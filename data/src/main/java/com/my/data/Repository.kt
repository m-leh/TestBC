package com.my.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import com.my.data.local.AppDatabase
import com.my.data.remote.RetrofitClient
import com.my.domain.Album
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.HttpUrl

/**
 * Manage local and remote data
 */
open class Repository(context: Context) {

    // DAO
    private val dataDAO = AppDatabase.getInstance(context)?.dataDao()

    // To be able to cancel launched coroutines (if any)
    private val job = Job()

    // The coroutine runs using the dispatcher by default
    private val coroutineScope = CoroutineScope(job + Dispatchers.Default)

    @VisibleForTesting
    var onAlbumsChange: (List<Album>) -> Unit = { albums ->
        coroutineScope.launch {
            dataDAO?.removeAll()
            dataDAO?.insertAll(albums)
        }
    }

    fun open() {
        val retrofitBuilder =
            RetrofitClient(
                HttpUrl.parse("https://static.leboncoin.fr/")!!,
                onAlbumsChange
            )
        retrofitBuilder.loadData()
    }

    fun loadAlbums(): LiveData<List<Album>>? {
        return dataDAO?.loadAlbums()
    }

    fun loadAlbums(id: Long): LiveData<List<Album>>? {
        return dataDAO?.loadAlbumById(id)
    }

    fun loadAlbumIds(): LiveData<List<Long>>? {
        return dataDAO?.loadAlbumIds()
    }

    fun close() {
        AppDatabase.destroyInstance()
        job.cancel()
    }

}