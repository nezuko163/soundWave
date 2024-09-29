package com.nezuko.data.source

import android.content.Context
import android.util.Log
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer
import com.CodeBoy.MediaFacer.mediaHolders.audioContent
import com.nezuko.data.di.Dispatcher
import com.nezuko.data.model.Audio
import com.nezuko.data.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "LocalSource"

@Singleton
class LocalSource @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher
) {
    val localTracksPlaylist = Playlist(
        id = -1L,
        title = "Файлы на устройстве",
        owner_id = "",
        ownerName = "Плейлист",
        artUrl = "",
        tracksIdList = arrayListOf(),
        tracksList = arrayListOf(),
        dateCreated = -1L,
        dateModified = -1L
    )

    fun loadLocalTracks() {
        val lstAudio = ArrayList<Audio>()

        MediaFacer.withAudioContex(context)
            .getAllAudioContent(AudioGet.externalContentUri)
            .forEachIndexed { index: Int, it: audioContent ->
                if (it.duration == 0L) return@forEachIndexed
                val audio = Audio(
                    id = -it.musicID,
                    title = it.title,
                    artist = it.artist,
                    album = it.album,
                    artUrl = it.art_uri.toString(),
                    mediaUrl = it.assetFileStringUri,
                    duration = it.duration,
                    owner_id = "",
                    dateAdded = it.date_added,
                    queueId = index.toLong()
                )
                Log.i(TAG, "getAudios: ${it.name}")

                lstAudio.add(audio)
            }
        Log.i(TAG, "loadLocalTracks: $lstAudio")
        localTracksPlaylist.tracksList = lstAudio
    }

    var localPlaylists: List<Playlist> = listOf()

    suspend fun loadLocalPlaylists(): Playlist {
        TODO()
    }
}