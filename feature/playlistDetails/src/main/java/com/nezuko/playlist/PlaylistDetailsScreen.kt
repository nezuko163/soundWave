package com.nezuko.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.ui.theme.Spacing

@Composable
fun PlaylistDetailsScreen(
    modifier: Modifier = Modifier,
    playlist: Playlist
) {
    Box(modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(Spacing.default.medium)
        ) {
            items(items = playlist.tracksList.toList()) { track: Audio ->
                Text(text = track.title)
                Spacer(modifier.padding(Spacing.default.tiny))
                Text(text = track.artist)
            }
        }
    }
}