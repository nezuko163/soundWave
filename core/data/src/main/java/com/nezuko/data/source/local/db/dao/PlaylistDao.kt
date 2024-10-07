package com.nezuko.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nezuko.data.source.local.db.entities.AudioEntity
import com.nezuko.data.source.local.db.entities.PlaylistAudioRef
import com.nezuko.data.source.local.db.entities.PlaylistEntity
import com.nezuko.data.source.local.db.entities.PlaylistWithAudio
import kotlinx.coroutines.flow.Flow

//@Dao
//interface PlaylistDao {

//
//    @Query("SELECT * FROM playlistentity WHERE id = :id")
//    suspend fun getPlaylistById(id: Long): PlaylistEntity
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(playlistEntity: PlaylistEntity)
//
//    @Delete
//    suspend fun delete(playlistEntity: PlaylistEntity)
//
//    @Update
//    suspend fun update(playlistEntity: PlaylistEntity)
//}

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    fun getAll(): Flow<List<PlaylistEntity>>

    @Transaction
    @Query("SELECT * FROM playlist WHERE playlist_id = :id")
    suspend fun getPlaylistById(id: Long): PlaylistWithAudio

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudioIntoPlaylist(playlistAudioRef: PlaylistAudioRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAudio(audioEntity: AudioEntity)

}