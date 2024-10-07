package com.nezuko.data.source.local.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.nezuko.data.model.Playlist

data class PlaylistWithAudio(
    @Embedded val playlistEntity: PlaylistEntity,
    @Relation(
        parentColumn = "playlist_id",
        entityColumn = "audio_id",
        associateBy = Junction(PlaylistAudioRef::class)
    )
    val audios: List<AudioEntity>


) {
    fun toPlaylist() = Playlist(
        id = playlistEntity.id,
        title = playlistEntity.title,
        ownerId = "zxc",
        ownerName = "Локальный плейлист",
        artUrl = playlistEntity.artUrl,
        tracksList = ArrayList(audios.map { it.toAudio() }),
        tracksIdList = ArrayList(audios.map { it.id }),
        dateModified = playlistEntity.dateModified,
        dateCreated = playlistEntity.dateCreated
    )
}
