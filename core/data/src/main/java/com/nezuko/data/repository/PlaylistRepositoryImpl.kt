package com.nezuko.data.repository

import android.util.Log
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.data.source.local.LocalSource
import com.nezuko.data.source.RemoteSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class PlaylistRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) : PlaylistRepository {

    private val TAG = "PlaylistRepositoryImpl"

    private val _playlists = MutableStateFlow<ArrayList<Playlist>>(arrayListOf())
    override val playlists: StateFlow<ArrayList<Playlist>>
        get() = _playlists

    private val _loadedTracks = MutableStateFlow<HashSet<Audio>>(hashSetOf())
    override val loadedTracks: StateFlow<HashSet<Audio>>
        get() = _loadedTracks

    private val _localPlaylists: ArrayList<Playlist> = arrayListOf()
    private val _remotePlaylists: ArrayList<Playlist> = arrayListOf()

    private var isLocalPlaylistsLoaded = false
    private var isRemotePlaylistsLoaded = false


    override fun startLoading() {
        _playlists.value
    }

    override suspend fun loadLocalPlaylists() {
        if (_localPlaylists.isEmpty()) {
            _localPlaylists.add(localSource.localTracksPlaylist)
            _playlistsWithLoadedTracks.update {
                it.apply { add(localSource.localTracksPlaylist) }
            }
        }

        if (localSource.localPlaylists.isNotEmpty()) {
            _localPlaylists.addAll(localSource.localPlaylists)
        }

        isLocalPlaylistsLoaded = true

        if (isRemotePlaylistsLoaded) endLoading()
    }

    override suspend fun loadLocalTracks() {
        Log.i(TAG, "loadLocalTracks: start")
        _loadedTracks.update { it.apply { addAll(localSource.loadLocalTracks()) } }
    }

    override suspend fun loadRemotePlaylists() {
        isRemotePlaylistsLoaded = true
        if (isLocalPlaylistsLoaded) endLoading()
    }

    private fun endLoading() {
        if (_localPlaylists.isNotEmpty() || _remotePlaylists.isNotEmpty()) {
            _playlists.update { (_localPlaylists + _remotePlaylists) as ArrayList<Playlist> }
        }
    }

    private val _playlistsWithLoadedTracks = MutableStateFlow<ArrayList<Playlist>>(arrayListOf())

    override val playlistsWithLoadedTracks: StateFlow<ArrayList<Playlist>>
        get() = _playlistsWithLoadedTracks

    private val _lastLoadedPlaylist = MutableStateFlow(Playlist.none())
    override val lastLoadedPlaylist: StateFlow<Playlist>
        get() = _lastLoadedPlaylist


    override suspend fun loadPlaylistById(playlistId: Long) {
        var lookingPlaylist = _playlistsWithLoadedTracks.value.find { it.id == playlistId }

        if (lookingPlaylist == null) {
            if (playlistId > 0) {
                lookingPlaylist = Playlist.none()
            }
            lookingPlaylist = Playlist.none()
        }

        _lastLoadedPlaylist.update { lookingPlaylist }
    }
}
