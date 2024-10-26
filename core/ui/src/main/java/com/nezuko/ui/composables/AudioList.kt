package com.nezuko.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AudioList(
    modifier: Modifier = Modifier,
    playlist: Playlist,
    onAudioClick: (Audio) -> Unit,
    onAudioMoreClick: (Audio) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var lastAudioClicked by remember { mutableStateOf(Audio.none()) }

    if (showBottomSheet) {
        AudioBottomSheet(
            audio = lastAudioClicked,
            sheetState = sheetState,
            coroutineScope = scope,
            onSheetClosed = {
                showBottomSheet = false
            }
        )
    }

    LazyColumn(
        modifier = modifier,
    ) {
        itemsIndexed(
            items = playlist.tracksList.toList(),
            key = { index: Int, audio: Audio -> audio.id }
        ) { _: Int, audio: Audio ->
            AudioCard(
                audio = audio,
                onClick = onAudioClick,
                rightActionButton = {
                    IconButton(onClick = { onAudioMoreClick(audio) }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AudioBottomSheet(
    modifier: Modifier = Modifier,
    audio: Audio,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    onSheetClosed: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion { onSheetClosed() }
        },
        sheetState = sheetState
    ) {
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