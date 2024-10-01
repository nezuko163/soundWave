package com.nezuko.data.di

import android.content.Context
import com.nezuko.data.repository.AuthRepositoryImpl
import com.nezuko.data.repository.PermissionRepositoryImpl
import com.nezuko.data.repository.PlaylistRepositoryImpl
import com.nezuko.data.source.local.LocalSource
import com.nezuko.data.source.RemoteSource
import com.nezuko.data.repository.AuthRepository
import com.nezuko.data.repository.PermissionRepository
import com.nezuko.data.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAuthRepo(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindPlaylistRepo(impl: PlaylistRepositoryImpl): PlaylistRepository

    @Binds
    @Singleton
    fun bindPermissionRepository(impl: PermissionRepositoryImpl): PermissionRepository

    companion object {
        @Provides
        @Singleton
        fun provideLocalSource(
            @ApplicationContext context: Context,
            @Dispatcher(MyDispatchers.IO) dispatcher: CoroutineDispatcher
        ) = LocalSource(context = context, dispatcher = dispatcher)

        @Provides
        @Singleton
        fun provideRemoteSource() = RemoteSource()
    }
}