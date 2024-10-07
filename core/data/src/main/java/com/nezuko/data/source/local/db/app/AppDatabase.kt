package com.nezuko.data.source.local.db.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nezuko.data.source.local.db.dao.PlaylistDao
import com.nezuko.data.source.local.db.entities.AudioEntity
import com.nezuko.data.source.local.db.entities.PlaylistAudioRef
import com.nezuko.data.source.local.db.entities.PlaylistEntity

@Database(
    entities = [PlaylistEntity::class, AudioEntity::class, PlaylistAudioRef::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}