package com.nezuko.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezuko.data.model.Playlist
import com.nezuko.ui.R
import com.nezuko.ui.theme.Spacing

private const val TAG = "PlaylistCard"

@Composable
fun PlaylistCard(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onPlaylistClick: (Playlist) -> Unit,
    onPlaylistMoreClick: (Playlist) -> Unit
) {
    Log.i(TAG, "PlaylistCard: $playlist")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onPlaylistClick(playlist) },
    ) {
        ImageFromURI(
            modifier = Modifier
                .size(100.dp)
                .padding(Spacing.default.small),
            url = playlist.artUrl,
            errorImageResource = R.drawable.audio
        )


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)

        ) {
            Text(
                text = playlist.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(Alignment.CenterVertically)

            )

            Text(
                text = playlist.ownerName,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentHeight(Alignment.Top)
            )
        }

        IconButton(
            onClick = { onPlaylistMoreClick(playlist) },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(30.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "больше"
            )
        }
    }
}