package com.nezuko.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nezuko.data.model.Playlist
import com.nezuko.ui.R
import com.nezuko.ui.theme.Spacing

@Composable
fun PlaylistCard(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onPlaylistClick: (Playlist) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .width(100.dp)
            .clickable { onPlaylistClick(playlist) },
    ) {
        ImageFromInet(
            modifier = Modifier
                .size(100.dp)
                .padding(Spacing.default.small),
            url = playlist.artUrl,
            errorImageResource = R.drawable.audio
        )

        Spacer(modifier = Modifier.padding(Spacing.default.tiny))

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = playlist.title,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.Start)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
            Text(
                text = playlist.title,
                fontSize = 17.sp,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.Start)
                    .wrapContentHeight(Alignment.Top),
            )
        }
    }
}

@Preview
@Composable
private fun PlaylistCardPrev() {
    val pl = Playlist(
        -1L, "залупа", "asd", "", "", arrayListOf(), arrayListOf(), -1L, -1L
    )

    PlaylistCard(playlist = pl) {

    }
}