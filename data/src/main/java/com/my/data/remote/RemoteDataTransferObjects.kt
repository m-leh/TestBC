package com.my.data.remote

import com.my.data.local.DataBaseAlbum
import com.my.domain.Album

data class NetworkAlbum(
    val albumId: Int,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String?
)

/**
 * Convert Network results to database objects
 */
fun List<NetworkAlbum>.asDomainModel(): List<Album> {
    return map {
        Album(
            id = it.id,
            title = it.title,
            albumId = it.albumId,
            url = it.url
        )
    }
}


/**
 * Convert Network results to database objects
 */
fun List<NetworkAlbum>.asDatabaseModel(): List<DataBaseAlbum> {
    return map {
        DataBaseAlbum(
            id = it.id,
            title = it.title,
            albumId = it.albumId,
            url = it.url
        )
    }
}