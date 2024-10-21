package com.nezuko.playlist

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "PlaylistDetailsRoute"

@Composable
fun PlaylistDetailsRoute(
    modifier: Modifier = Modifier,
    id: Long,
    onNavigateBack: () -> Unit,
    vm: PlaylistDetailViewModel = hiltViewModel()
) {
    val playlist by vm.playlist.collectAsState()
    vm.loadPlaylist(id)

    Log.i(TAG, "PlaylistDetailsRoute: $playlist")
    Log.i(TAG, "PlaylistDetailsRoute: id = $id")

    PlaylistDetailsScreen(
        modifier = modifier,
        playlist = playlist
    )
}