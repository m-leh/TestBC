package com.my.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.my.domain.Album

@Entity(tableName = "albums")
data class DataBaseAlbum(
    @PrimaryKey var id: Long, var albumId: Int,
    var title: String?,
    var url: String?
)

/**
 * Map DatabaseAlbums to domain entities
 */
fun List<DataBaseAlbum>.asDomainModel(): List<Album> {
    return map {
        Album(
            id = it.id,
            title = it.title,
            albumId = it.albumId,
            url = it.url
        )
    }
}