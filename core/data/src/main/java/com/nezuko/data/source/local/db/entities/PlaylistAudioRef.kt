package com.nezuko.data.source.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "playlist_audio_ref",
    primaryKeys = ["audio_id", "playlist_id"],
    indices = [Index("playlist_id")]
)
data class PlaylistAudioRef(
    @ColumnInfo(name = "audio_id") val audioId: Long,
    @ColumnInfo(name = "playlist_id") val playlistId: Long,
)
