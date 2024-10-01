package com.nezuko.playlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlaylistRoute(
    modifier: Modifier = Modifier,
    id: Long,
    onNavigateBack: () -> Unit
) {
    PlaylistScreen(modifier = modifier)
}