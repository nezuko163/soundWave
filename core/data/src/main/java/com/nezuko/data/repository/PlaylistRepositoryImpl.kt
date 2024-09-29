package com.nezuko.data.repository

import com.nezuko.data.source.LocalSource
import com.nezuko.data.source.RemoteSource
import com.nezuko.data.model.Playlist
import com.nezuko.data.model.ResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class PlaylistRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) : PlaylistRepository {

    private val _playlists = MutableStateFlow<ResultModel<ArrayList<Playlist>>>(ResultModel.none())
    override val playlists: StateFlow<ResultModel<ArrayList<Playlist>>>
        get() = _playlists

    private val _localPlaylists = MutableStateFlow<ArrayList<Playlist>>(arrayListOf())
    override val localPlaylists: StateFlow<ArrayList<Playlist>>
        get() = _localPlaylists

    private val _remotePlaylists = MutableStateFlow<ArrayList<Playlist>>(arrayListOf())
    override val remotePlaylists: StateFlow<ArrayList<Playlist>>
        get() = _remotePlaylists

    override fun startLoading() {
        _playlists.value = ResultModel.loading()
    }

    override suspend fun loadLocalPlaylists() {
        _localPlaylists.value = _localPlaylists.value.apply { add(localSource.localTracksPlaylist) }
        localSource.loadLocalPlaylists()
        _localPlaylists.value.addAll(localSource.localPlaylists)
    }

    override suspend fun loadLocalTracks() {
        localSource.loadLocalTracks()
    }

    override suspend fun loadRemotePlaylists() {

    }
}