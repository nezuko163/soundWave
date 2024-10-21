package com.nezuko.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun SearchScreen(modifier: Modifier = Modifier) {
    var a by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier.clickable { a++ }) {
        Text(
            text = "SEARCH",
        )

        Text(
            text = "$a"
        )
    }
}