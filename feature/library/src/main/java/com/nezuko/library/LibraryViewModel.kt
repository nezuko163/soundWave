package com.nezuko.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.di.Dispatcher
import com.nezuko.data.di.MyDispatchers
import com.nezuko.data.repository.PermissionRepository
import com.nezuko.data.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository,
    private val permissionRepository: PermissionRepository,
    @Dispatcher(MyDispatchers.IO) private val IODispatcher: CoroutineDispatcher
) : ViewModel() {
    val playlists = playlistRepository.playlists
    fun loadPlaylists() {
        viewModelScope.launch(IODispatcher) {
            playlistRepository.startLoading()
            playlistRepository.loadLocalTracks()
            playlistRepository.loadLocalPlaylists()
            playlistRepository.loadRemotePlaylists()
        }
    }

    val audioPermission = permissionRepository.audioPermissionIsGranted

    fun checkAudioPermission() {
//        if (!audioPermission.value) {
//            permissionRepository.requestAudioPermission()
//        }
    }

    fun onPermissionRequest(requestCode: Int, grantResult: Boolean) {
        permissionRepository.onRequestPermissionsResult(requestCode, grantResult)
    }

}