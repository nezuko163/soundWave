package com.nezuko.ui.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nezuko.data.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsList(
    modifier: Modifier = Modifier,
    playlists: List<Playlist>,
    onPlaylistClick: (Playlist) -> Unit,
    onPlaylistMoreClick: (Playlist) -> Unit,
    onRedactClick: (Playlist) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var lastPlaylistClicked by remember { mutableStateOf(Playlist.none()) }



    if (showBottomSheet) {
        PlaylistBottomSheet(
            playlist = lastPlaylistClicked,
            sheetState = sheetState,
            coroutineScope = scope,
            onSheetClosed = {
                showBottomSheet = false
            },
            onRedact = { playlist ->
                onRedactClick(playlist)
            }
        )
    }

    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(
            items = playlists,
            key = { _: Int, playlist: Playlist ->
                playlist.id
            }
        ) { _: Int, playlist: Playlist ->

            PlaylistCard(
                playlist = playlist,
                onPlaylistClick = onPlaylistClick,
                onPlaylistMoreClick = {
                    lastPlaylistClicked = playlist
                    onPlaylistMoreClick(playlist)
                    showBottomSheet = true
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistBottomSheet(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSheetClosed: () -> Unit,
    onRedact: (Playlist) -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion { onSheetClosed() }
        },
        sheetState = sheetState
    ) {

        Text(
            text = "редактировать",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion { onSheetClosed() }
                    onRedact(playlist)
                }
        )


        Text(
            text = "123",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion { onSheetClosed() }
                }
        )

    }
}

