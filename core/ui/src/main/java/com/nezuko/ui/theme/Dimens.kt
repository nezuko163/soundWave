package com.nezuko.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val playlistIconSmall: Dp,
    val playlistIconBig: Dp,
    val audioIconSmall: Dp,
    val audioIconBig: Dp,
    val extraLarge: Dp
) {
    companion object {
        val default = Dimens(
            playlistIconSmall = 100.dp,
            playlistIconBig = 8.dp,
            audioIconSmall = 16.dp,
            audioIconBig = 24.dp,
            extraLarge = 32.dp
        )
    }
}
