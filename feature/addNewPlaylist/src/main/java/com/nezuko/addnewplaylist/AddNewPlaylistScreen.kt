package com.nezuko.addnewplaylist

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import com.nezuko.ui.composables.ImageFromURI
import com.nezuko.ui.theme.Dimens
import com.nezuko.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPlaylistScreen(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    var playlistName by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(horizontal = Spacing.default.small),
                title = { Text(text = "Новый плейлист") },
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "отказ")
                    }
                },
                actions = {
                    IconButton(onClick = onDoneClick) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "отказ")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Spacing.default.small)
        ) {
            Row(
                modifier = Modifier
                    .padding(Spacing.default.small)
                    .border(2.dp, Color.Black),
                verticalAlignment = Alignment.CenterVertically
                ) {
                ImageFromURI(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(Dimens.default.playlistIconSmall)
                    ,
                    url = imageUri,
                    errorImageResource = com.nezuko.ui.R.drawable.audio
                )

                Spacer(modifier = Modifier.padding(Spacing.default.small))

                Column(
                    modifier = Modifier
                ) {
                    Text(text = "Название", modifier)

                    Spacer(modifier = Modifier.padding(Spacing.default.tiny))

                    TextField(
                        modifier = Modifier
                            .weight(1f),
                        value = playlistName,
                        onValueChange = { playlistName = it }
                    )
                }
            }
        }
    }
}