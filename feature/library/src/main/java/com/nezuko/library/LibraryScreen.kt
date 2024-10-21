package com.nezuko.library

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nezuko.data.model.Playlist
import com.nezuko.ui.composables.PlaylistsList

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun LibraryScreen(
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit,
    onAddNewPlaylistClick: (Playlist) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Бибилиотека") },
                actions = {
                    IconButton(onClick = { onAddNewPlaylistClick(Playlist.none()) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }
            )
        }
    ) { padding ->

        if (playlists.isNotEmpty()) {
            PlaylistsList(
                modifier.padding(padding),
                playlists = playlists,
                onPlaylistClick = onPlaylistClick,
                onPlaylistMoreClick = {},
                onRedactClick = { playlist ->
                    onAddNewPlaylistClick(playlist)
                }
            )
        } else {
            Text(text = "загрузка")
        }
    }
}

