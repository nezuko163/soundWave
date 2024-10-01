package com.nezuko.playlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlaylistDetailsRoute(
    modifier: Modifier = Modifier,
    id: Long,
    onNavigateBack: () -> Unit,
    vm: PlaylistDetailViewModel = hiltViewModel()
) {
    val playlistId by rememberSaveable { mutableLongStateOf(id) }
    val playlist by vm.playlist.collectAsState()

    LaunchedEffect(playlistId) {
        vm.loadPlaylist(playlistId)
    }

    PlaylistDetailsScreen(
        modifier = modifier,
            playlist = playlist
        )
}