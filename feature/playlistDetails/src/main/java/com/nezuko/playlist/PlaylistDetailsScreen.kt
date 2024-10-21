package com.nezuko.playlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nezuko.data.model.Playlist
import com.nezuko.ui.composables.AudioList

@Composable
internal fun PlaylistDetailsScreen(
    modifier: Modifier = Modifier,
    playlist: Playlist
) {
    AudioList(
        playlist = playlist,
        onAudioClick = {},
        onAudioMoreClick = {}
    )
}