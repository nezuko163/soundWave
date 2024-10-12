package com.nezuko.data.source

import com.nezuko.data.model.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RemoteSource {
    val remotePlaylists: StateFlow<ArrayList<Playlist>>
        get() = MutableStateFlow(arrayListOf())
}