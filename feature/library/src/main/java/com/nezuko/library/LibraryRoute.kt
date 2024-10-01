package com.nezuko.library

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.REQUEST_CODE_AUDIO
import com.nezuko.data.REQUEST_CODE_NOTIFICATION
import com.nezuko.data.model.Playlist
import com.nezuko.data.model.ResultModel
import com.nezuko.ui.util.permissionLauncher

private const val TAG = "LibraryRoute"

@Composable
fun LibraryRoute(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit,
    vm: LibraryViewModel = hiltViewModel()
) {
    val playlists by vm.playlists.collectAsState()
    val audioPermissionIsGranted by vm.audioPermission.collectAsState()
    val isFirstLoading by remember { mutableStateOf(true) }

    val launcher = permissionLauncher(
        onGranted = { vm.onPermissionRequest(REQUEST_CODE_AUDIO, true) },
        onFailure = { vm.onPermissionRequest(REQUEST_CODE_AUDIO, false) },
    )

    Log.i(TAG, "LibraryRoute: $audioPermissionIsGranted")

    LaunchedEffect(isFirstLoading) {
        if (!audioPermissionIsGranted) {
            val permission =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_AUDIO
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

            launcher.launch(permission)
        }
    }

    LaunchedEffect(audioPermissionIsGranted) {
        if (audioPermissionIsGranted) {
            vm.loadPlaylists()
        }
    }

    if (playlists.data != null && playlists.status == ResultModel.Status.SUCCESS) {
        LibraryScreen(
            modifier = modifier,
            playlists = playlists.data!!,
            onPlaylistClick = onPlaylistClick
        )
    }
    else if (playlists.status == ResultModel.Status.LOADING) {
        Box(modifier = modifier.fillMaxSize()) {
            Text(text = "Загрузка", modifier = Modifier.align(Alignment.Center))
        }
    }
}