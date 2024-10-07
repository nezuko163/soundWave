package com.nezuko.data.source.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nezuko.data.model.Playlist

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey
    @ColumnInfo(name = "playlist_id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "art_url") val artUrl: String,
    @ColumnInfo(name = "date_created") val dateCreated: Long,
    @ColumnInfo(name = "date_modified") val dateModified: Long
) {
    public fun toPlaylist() = Playlist(
        id = -id,
        title = title,
        ownerId = "owner",
        ownerName = "Файлы на устройстве",
        artUrl = artUrl,
        dateModified = dateModified,
        dateCreated = dateCreated,
        tracksIdList = arrayListOf()
    )
}

fun Playlist.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        title = this.title,
        artUrl = this.artUrl,
        dateCreated = this.dateCreated,
        dateModified = this.dateModified
    )
}