package com.nezuko.data.repository

import android.util.Log
import com.nezuko.data.source.LocalSource
import com.nezuko.data.source.RemoteSource
import com.nezuko.data.model.Playlist
import com.nezuko.data.model.ResultModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class PlaylistRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) : PlaylistRepository {

    private val TAG = "PlaylistRepositoryImpl"
    private val _playlists = MutableStateFlow<ResultModel<ArrayList<Playlist>>>(ResultModel.none())
    override val playlists: StateFlow<ResultModel<ArrayList<Playlist>>>
        get() = _playlists

    private val _localPlaylists: ArrayList<Playlist> = arrayListOf()
    override val localPlaylists: List<Playlist>
        get() = _localPlaylists

    private val _remotePlaylists: ArrayList<Playlist> = arrayListOf()
    override val remotePlaylists: List<Playlist>
        get() = _remotePlaylists

    override fun startLoading() {
        _playlists.value = ResultModel.loading()
    }

    override suspend fun loadLocalPlaylists() {

        if (_localPlaylists.isEmpty()) {
            _localPlaylists.add(localSource.localTracksPlaylist)
        }

        if (localSource.localPlaylists.isNotEmpty()) {
            _localPlaylists.addAll(localSource.localPlaylists)
        }
    }

    override suspend fun loadLocalTracks() {
        Log.i(TAG, "loadLocalTracks: start")
        localSource.loadLocalTracks()
    }

    override suspend fun loadRemotePlaylists() {

    }
}