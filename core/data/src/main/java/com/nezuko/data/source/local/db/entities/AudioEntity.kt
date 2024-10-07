package com.nezuko.data.source.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nezuko.data.model.Audio

@Entity(tableName = "audio_table")
data class AudioEntity(
    @PrimaryKey
    @ColumnInfo(name = "audio_id")
    val id: Long,
    @ColumnInfo(name = "audio_title") val title: String,
    @ColumnInfo(name = "audio_artist") val artist: String,
    @ColumnInfo(name = "audio_album") val album: String,
    @ColumnInfo(name = "audio_art_url") val artUrl: String,
    @ColumnInfo(name = "audio_media_url") val mediaUrl: String,
    @ColumnInfo(name = "audio_duration") val duration: Long,
    @ColumnInfo(name = "audio_owner_id") val ownerId: String,
    @ColumnInfo(name = "audio_date_added") val dateAdded: Long,
    @ColumnInfo(name = "audio_queue_id") val queueId: Long
) {
    fun toAudio() = Audio(
        id = id,
        title = title,
        artist = artist,
        album = album,
        artUrl = artUrl,
        mediaUrl = mediaUrl,
        duration = duration,
        ownerId = ownerId,
        dateAdded = dateAdded,
    )
}