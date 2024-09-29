package com.nezuko.data.repository

import com.nezuko.data.model.Playlist
import com.nezuko.data.model.ResultModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface PlaylistRepository {
    val playlists: StateFlow<ResultModel<ArrayList<Playlist>>>
    val localPlaylists: List<Playlist>
    val remotePlaylists: List<Playlist>

    fun startLoading()
    suspend fun loadLocalTracks()
    suspend fun loadLocalPlaylists()
    suspend fun loadRemotePlaylists()

}