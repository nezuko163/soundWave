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
    private val _remotePlaylists: ArrayList<Playlist> = arrayListOf()

    private var isLocalPlaylistsLoaded = false
    private var isRemotePlaylistsLoaded = false

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

        isLocalPlaylistsLoaded = true

        if (isRemotePlaylistsLoaded) endLoading()
    }

    override suspend fun loadLocalTracks() {
        Log.i(TAG, "loadLocalTracks: start")
        localSource.loadLocalTracks()
    }

    override suspend fun loadRemotePlaylists() {

        isRemotePlaylistsLoaded = true

        if (isLocalPlaylistsLoaded) endLoading()
    }

    private fun endLoading() {
        if (_localPlaylists.isNotEmpty() || _remotePlaylists.isNotEmpty()) {
            _playlists.update {
                ResultModel.success((_localPlaylists + _remotePlaylists) as ArrayList<Playlist>)
            }
        }
    }


}