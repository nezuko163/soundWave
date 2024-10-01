package com.nezuko.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {
    val playlist = playlistRepository.lastLoadedPlaylist

    fun loadPlaylist(playlistId: Long) {
        viewModelScope.launch {
            playlistRepository.loadPlaylistById(playlistId)
        }
    }
}