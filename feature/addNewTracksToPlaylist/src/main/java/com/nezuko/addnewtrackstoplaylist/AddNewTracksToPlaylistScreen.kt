package com.nezuko.addnewtrackstoplaylist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.ui.composables.AudioCard
import java.util.Queue

private const val TAG = "AddNewTracksToPlaylistScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddNewTracksToPlaylistScreen(
    modifier: Modifier = Modifier,
    tracks: List<Audio>,
    playlist: Playlist,
    onCloseClick: () -> Unit,
    onSearchClick: () -> Unit,
    onDoneClick: (tracks: ArrayList<Audio>) -> Unit,
) {
    val scroll = rememberScrollState()
    val newTracks = remember { mutableStateListOf<Audio>() }
    val newTracksId = remember { mutableStateListOf<Long>() }

    if (playlist.tracksIdList.isNotEmpty()) {
        Log.i(TAG, "AddNewTracksToPlaylistScreen: id = ${playlist.tracksIdList}")
        Log.i(TAG, "AddNewTracksToPlaylistScreen: first = ${playlist.tracksList.first()}")
    }

    newTracks.addAll(playlist.tracksList)
    newTracksId.addAll(playlist.tracksIdList)

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Добавить музыку")
                },
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            )

            HorizontalDivider(thickness = 1.dp, color = Color.Black.copy(alpha = 0.2f))
        },
        bottomBar = {
            Button(
                onClick = { onDoneClick(ArrayList(newTracks)) },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(text = "Сохранить")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scroll)
        ) {
            tracks.forEach { audio ->
                AudioCard(
                    audio = audio,
                    rightActionButton = {
                        Checkbox(
                            checked = (newTracksId.contains(audio.id)),
                            onCheckedChange = { isChecked ->
                                Log.i(TAG, "AddNewTracksToPlaylistScreen: $isChecked")
                                if (!isChecked) {
                                    newTracksId.remove(audio.id)
                                    newTracks.remove(audio)
                                } else {
                                    newTracksId.add(audio.id)
                                    newTracks.add(audio)
                                }
                            }
                        )
                    },
                    onClick = {}
                )
            }
        }
    }
}