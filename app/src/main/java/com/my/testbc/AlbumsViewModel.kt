package com.my.testbc

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.my.data.Repository
import com.my.domain.Album

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {

    @VisibleForTesting
    lateinit var repository: Repository

    fun open() {
        repository = Repository(getApplication())
        repository.open()
    }

    fun loadAlbums(): LiveData<List<Album>>? {
        return repository.loadAlbums()
    }

    fun loadAlbums(id: Long): LiveData<List<Album>>? {
        return repository.loadAlbums(id)
    }

    fun loadAlbumIds(): LiveData<List<Long>>? {
        return repository.loadAlbumIds()
    }

    fun close() {
        repository.close()
    }

}