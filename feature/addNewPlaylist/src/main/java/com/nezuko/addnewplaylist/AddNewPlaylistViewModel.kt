package com.nezuko.addnewplaylist

import android.util.Log
import androidx.collection.emptyLongList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.data.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewPlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    companion object {
        private const val TAG = "AddNewPlaylistViewModel"
    }

    val playlist = playlistRepository.lastLoadedPlaylist

    fun loadPlaylist(playlistId: Long) {
        viewModelScope.launch {
            playlistRepository.loadPlaylistById(playlistId)
        }
    }

    private val _tracks = MutableStateFlow<ArrayList<Audio>>(arrayListOf())
    val tracks = _tracks.asStateFlow()

    fun setTracks(tracks: ArrayList<Audio>) {
        Log.i(TAG, "setTracks: $tracks")
        _tracks.update { tracks }
    }
}