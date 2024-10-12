package com.nezuko.data.source.local.db

import android.content.Context
import com.nezuko.data.di.ApplicationScope
import com.nezuko.data.di.Dispatcher
import com.nezuko.data.di.MyDispatchers
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import com.nezuko.data.source.local.db.app.AppDatabase
import com.nezuko.data.source.local.db.entities.PlaylistAudioRef
import com.nezuko.data.source.local.db.entities.PlaylistEntity
import com.nezuko.data.source.local.db.entities.toPlaylistEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class DBManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(MyDispatchers.IO) private val IODispatcher: CoroutineDispatcher,
) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.playlistDao()

    fun localPlaylists(): Flow<List<PlaylistEntity>> =
        dao.getAll()

    suspend fun getPlaylistById(id: Long): Playlist {
        return dao.getPlaylistById(id).toPlaylist()
    }

    suspend fun insertPlaylist(playlist: Playlist) {
        return dao.insertPlaylist(playlist.toPlaylistEntity())
    }

    suspend fun insertPlaylistWithTracks(playlist: Playlist, tracks: List<Audio>) {
        insertPlaylist(playlist)
        tracks.forEach { audio ->
            insertAudioIntoPlaylist(playlist, audio)
        }
    }

    suspend fun insertAudioIntoPlaylist(playlist: Playlist, audio: Audio) {
        return dao.insertAudioIntoPlaylist(
            PlaylistAudioRef(
                audioId = audio.id,
                playlistId = playlist.id
            )
        )
    }

//    val localPlaylists: Flow<List<Playlist>>
}