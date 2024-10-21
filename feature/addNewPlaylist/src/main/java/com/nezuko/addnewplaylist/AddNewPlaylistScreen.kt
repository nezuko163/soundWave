package com.nezuko.addnewplaylist

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.ui.composables.AudioCard
import com.nezuko.ui.composables.AudioList
import com.nezuko.ui.composables.ImageFromURI
import com.nezuko.ui.theme.Dimens
import com.nezuko.ui.theme.Gray
import com.nezuko.ui.theme.GrayText
import com.nezuko.ui.theme.Sky
import com.nezuko.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddNewPlaylistScreen(
    modifier: Modifier = Modifier,
    playlist: Playlist = Playlist.none(),
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
    onAddAudioClick: (Playlist) -> Unit,
) {
    val context = LocalContext.current
    var playlistName by remember { mutableStateOf(playlist.title) }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(text = "Новый плейлист", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "отказ",
                            tint = Sky
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDoneClick) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "отказ",
                            tint = Sky
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Spacing.default.small)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .padding(Spacing.default.small)
                    .height(Dimens.default.playlistIconSmall + Spacing.default.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageFromURI(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(Dimens.default.playlistIconSmall),
                    url = playlist.artUrl,
                    errorImageResource = com.nezuko.ui.R.drawable.audio
                )

                Spacer(modifier = Modifier.padding(Spacing.default.small))

                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = "Название",
                        modifier = modifier,
                        color = GrayText
                    )

                    Spacer(modifier = Modifier.padding(Spacing.default.tiny))

                    TextField(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(10.dp)),
                        value = playlistName,
                        onValueChange = { playlistName = it },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Gray,
                            disabledContainerColor = Gray,
                            unfocusedContainerColor = Gray,
                            disabledPlaceholderColor = GrayText,
                            focusedPlaceholderColor = GrayText,
                            unfocusedPlaceholderColor = GrayText,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(text = "жопа") },

                        )
                }
            }

            Spacer(modifier = Modifier.padding(Spacing.default.tiny))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAddAudioClick(playlist) }
                    .padding(Spacing.default.small)
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .height(IntrinsicSize.Max),
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = Sky
                )


                Spacer(modifier = Modifier.padding(Spacing.default.tiny))

                Text(text = "Добавить музыку", modifier = Modifier.weight(1f), color = Sky)
            }

            Spacer(modifier = Modifier.padding(Spacing.default.tiny))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = Spacing.default.medium),
                thickness = 2.dp,
                color = Sky.copy(alpha = 0.1f)
            )


            Column() {
                playlist.tracksList.forEach { audio ->
                    AudioCard(
                        audio = audio,
                        rightActionButton = {
                            IconButton(onClick = { }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "")
                            }
                        },
                        onClick = {
                            Toast.makeText(context, audio.title, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}