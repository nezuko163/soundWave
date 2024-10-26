package com.nezuko.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import com.nezuko.data.di.ApplicationScope
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.data.source.local.LocalSource
import com.nezuko.data.source.RemoteSource
import com.nezuko.data.source.local.db.DBManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class PlaylistRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
    private val dbManager: DBManager,
    @ApplicationScope private val coroutineScope: CoroutineScope
) : PlaylistRepository {

    private val TAG = "PlaylistRepositoryImpl"

    private val _playlists = MutableStateFlow<ArrayList<Playlist>>(arrayListOf())
    override val playlists: StateFlow<ArrayList<Playlist>>
        get() = _playlists

    private val _loadedTracks = MutableStateFlow<HashSet<Audio>>(hashSetOf())
    override val loadedTracks: StateFlow<HashSet<Audio>>
        get() = _loadedTracks

    private lateinit var _localPlaylists: StateFlow<List<Playlist>>
    private lateinit var _remotePlaylists: StateFlow<List<Playlist>>
    private lateinit var _localTracksPlaylist: Playlist

    private var isLocalPlaylistsLoaded = false
    private var isRemotePlaylistsLoaded = false


    override fun startLoading() {

    }

    override suspend fun loadLocalPlaylists() {
        _localPlaylists = dbManager.localPlaylists().map {
            it.map { playlistEntity ->
                playlistEntity.toPlaylist()
            }
        }.stateIn(coroutineScope, SharingStarted.Eagerly, listOf())

        isLocalPlaylistsLoaded = true

        if (isRemotePlaylistsLoaded) endLoading()
    }

    override suspend fun loadLocalTracks() {
        Log.i(TAG, "loadLocalTracks: start")

        localSource.loadLocalTracks()
        _localTracksPlaylist = localSource.localTracksPlaylist
        _playlistsWithLoadedTracks.value.add(_localTracksPlaylist)
        _loadedTracks.update { it.apply { addAll(_localTracksPlaylist.tracksList) } }
    }

    override suspend fun loadRemotePlaylists() {
        _remotePlaylists = remoteSource.remotePlaylists
        isRemotePlaylistsLoaded = true
        if (isLocalPlaylistsLoaded) endLoading()
    }

    private fun endLoading() {
        _playlists.value =
            ArrayList(_remotePlaylists.value + _localPlaylists.value + _localTracksPlaylist)

        Log.i(TAG, "endLoading: ${_playlists.value}")
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

        Log.i(TAG, "loadPlaylistById: $lookingPlaylist")
        
        _lastLoadedPlaylist.update { lookingPlaylist }
    }
}
