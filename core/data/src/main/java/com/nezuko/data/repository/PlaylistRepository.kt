package com.nezuko.data.repository

import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface PlaylistRepository {
    val playlists: StateFlow<ArrayList<Playlist>>
    val playlistsWithLoadedTracks: StateFlow<ArrayList<Playlist>>
    val loadedTracks: StateFlow<HashSet<Audio>>
    val lastLoadedPlaylist: StateFlow<Playlist>

    fun startLoading()
    suspend fun loadLocalTracks()
    suspend fun loadLocalPlaylists()
    suspend fun loadRemotePlaylists()

    suspend fun loadPlaylistById(playlistId: Long)
}