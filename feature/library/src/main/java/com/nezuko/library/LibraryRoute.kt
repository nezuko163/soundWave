package com.nezuko.library

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.REQUEST_CODE_AUDIO
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
    var isFirstLoading by rememberSaveable { mutableStateOf(true) }

    Log.i(TAG, "LibraryRoute: plailists - $playlists")
    Log.i(TAG, "LibraryRoute: audio - $audioPermissionIsGranted")
    Log.i(TAG, "LibraryRoute: isfirst - $isFirstLoading")

    val launcher = permissionLauncher(
        onGranted = { vm.onPermissionRequest(REQUEST_CODE_AUDIO, true) },
        onFailure = { vm.onPermissionRequest(REQUEST_CODE_AUDIO, false) },
    )

    LaunchedEffect(isFirstLoading) {
        if (!isFirstLoading) return@LaunchedEffect
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
        if (isFirstLoading) {
            Log.i(TAG, "LibraryRoute: audioPermissionIsGranted - $audioPermissionIsGranted")
            if (audioPermissionIsGranted) {
                vm.loadPlaylists()
            }
            isFirstLoading = false
        }
    }

    if (playlists.isNotEmpty()) {
        LibraryScreen(
            modifier = modifier,
            playlists = playlists,
            onPlaylistClick = onPlaylistClick
        )
    }
//    else if (playlists.status == ResultModel.Status.LOADING) {
//        Box(modifier = modifier.fillMaxSize()) {
//            Text(text = "Загрузка", modifier = Modifier.align(Alignment.Center))
//        }
//    }
}