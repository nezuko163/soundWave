package com.nezuko.addnewtrackstoplaylist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "AddNewTracksToPlaylistRoute"

@Composable
fun AddNewTracksToPlaylistRoute(
    modifier: Modifier = Modifier,
    id: Long? = null,
    onNavigateBack: () -> Unit,
    vm: AddNewTracksToPlaylistViewModel
) {
    val playlist by vm.playlist.collectAsState()
    val tracksForSearch by vm.tracksForSearch.collectAsState()
    var isLoadFinished = false

    LaunchedEffect(id) {
        vm.loadPlaylist(id ?: 0L)
        isLoadFinished = true
    }

    Log.i(TAG, "AddNewTracksToPlaylistRoute: ${playlist.tracksList}")

    AddNewTracksToPlaylistScreen(
        tracks = tracksForSearch.toList(),
        playlist = playlist,
        onCloseClick = onNavigateBack,
        onSearchClick = {},
        onDoneClick = { tracks ->
            playlist.tracksList = tracks
        }
    )
}