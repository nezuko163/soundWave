package com.nezuko.addnewtrackstoplaylist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.model.Audio

private const val TAG = "AddNewTracksToPlaylistRoute"

@Composable
fun AddNewTracksToPlaylistRoute(
    modifier: Modifier = Modifier,
    id: Long? = null,
    onNavigateBack: () -> Unit,
    onTracksAdded: (ArrayList<Audio>) -> Unit,
    vm: AddNewTracksToPlaylistViewModel
) {
    val playlist by vm.playlist.collectAsState()
    val tracksForSearch by vm.tracksForSearch.collectAsState()

    var isLoadFinished by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        vm.loadPlaylist(id ?: 0L)
        isLoadFinished = true
    }

    Log.i(TAG, "AddNewTracksToPlaylistRoute: load - $isLoadFinished")

    Log.i(TAG, "AddNewTracksToPlaylistRoute: ${playlist.tracksList}")

    if (isLoadFinished) {

        AddNewTracksToPlaylistScreen(
            tracks = tracksForSearch.toList(),
            playlist = playlist,
            onCloseClick = onNavigateBack,
            onSearchClick = {},
            onDoneClick = { tracks ->
                playlist.tracksList = tracks
                onTracksAdded(tracks)
                Log.i(TAG, "AddNewTracksToPlaylistRoute: ${playlist.tracksList}")
            }
        )
    }
}
