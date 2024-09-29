package com.nezuko.data.repository

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface PlaylistRepository {
    val playlists: StateFlow<com.nezuko.data.model.ResultModel<ArrayList<com.nezuko.data.model.Playlist>>>
    val localPlaylists: StateFlow<List<com.nezuko.data.model.Playlist>>
    val remotePlaylists: StateFlow<List<com.nezuko.data.model.Playlist>>

    fun startLoading()
    suspend fun loadLocalTracks()
    suspend fun loadLocalPlaylists()
    suspend fun loadRemotePlaylists()

}