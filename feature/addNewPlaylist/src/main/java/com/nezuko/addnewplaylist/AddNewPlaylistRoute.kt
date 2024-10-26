package com.nezuko.addnewplaylist

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.model.Playlist

private const val TAG = "AddNewPlaylistRoute"

@Composable
fun AddNewPlaylistRoute(
    modifier: Modifier = Modifier,
    id: Long?,
    onNavigateBack: () -> Unit,
    onDone: () -> Unit,
    vm: AddNewPlaylistViewModel,
    onAddNewTracksNavigate: (Playlist) -> Unit
) {
    Log.i(TAG, "AddNewPlaylistRoute: ")
    val playlist by vm.playlist.collectAsState()
    val tracks by vm.tracks.collectAsState()

    LaunchedEffect(id) {
        if (id != null) vm.loadPlaylist(id)
    }

    Log.i(TAG, "AddNewPlaylistRoute: $tracks")


    if (id != null) {
        AddNewPlaylistScreen(
            playlist = playlist,
            onCloseClick = onNavigateBack,
            onDoneClick = onDone,
            onAddAudioClick = onAddNewTracksNavigate
        )
    }

    else {
        AddNewPlaylistScreen(
            onCloseClick = onNavigateBack,
            onDoneClick = onDone,
            onAddAudioClick = onAddNewTracksNavigate
        )
    }
}