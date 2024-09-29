package com.nezuko.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.model.ResultModel

@Composable
fun LibraryRoute(
    modifier: Modifier = Modifier,
    vm: LibraryViewModel = hiltViewModel()
) {
    val playlists by vm.playlists.collectAsState()
    vm.loadPlaylists()

    if (playlists.data != null && playlists.status == ResultModel.Status.SUCCESS) {
        LibraryScreen(modifier = modifier, playlists = playlists.data!!)
    }
    
    if (playlists.status == ResultModel.Status.LOADING) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(text = "Загрузка", modifier = Modifier.align(Alignment.Center))
        }
    }


}