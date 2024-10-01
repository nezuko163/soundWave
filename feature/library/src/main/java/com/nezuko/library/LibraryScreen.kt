package com.nezuko.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nezuko.data.model.Playlist
import com.nezuko.ui.composables.PlaylistsList

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LibraryScreen(
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Бибилиотека") }
            )
        }
    ) { padding ->
        PlaylistsList(
            modifier.padding(padding),
            playlists = playlists,
            onPlaylistClick = onPlaylistClick,
            onPlaylistMoreClick = {}
        )

    }
}


