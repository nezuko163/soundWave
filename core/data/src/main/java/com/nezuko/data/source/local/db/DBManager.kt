package com.nezuko.data.source.local.db

import android.content.Context
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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(MyDispatchers.IO) private val IODispatcher: CoroutineDispatcher
) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.playlistDao()

    val localPlaylists: Flow<List<PlaylistEntity>>
        get() = dao.getAll()

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