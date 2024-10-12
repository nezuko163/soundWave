package com.nezuko.addnewplaylist

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddNewPlaylistRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onDone: () -> Unit,
    vm: AddNewPlaylistViewModel = hiltViewModel()
) {
    AddNewPlaylistScreen(
        imageUri = Uri.EMPTY,
        onCloseClick = onNavigateBack,
        onDoneClick = onDone
    )
}