package com.my.testbc

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.data.Repository
import com.my.domain.Album
import kotlinx.coroutines.launch

class AlbumsViewModel(@VisibleForTesting val repository: Repository) : ViewModel() {

    fun open() {
        viewModelScope.launch {
            repository.refreshAlbums()
        }
    }

    fun loadAlbums(): LiveData<List<Album>> {
        return repository.loadAlbums()
    }

    fun loadAlbums(id: Long): LiveData<List<Album>> {
        return repository.loadAlbums(id)
    }

    fun loadAlbumIds(): LiveData<List<Long>>? {
        return repository.loadAlbumIds()
    }

}