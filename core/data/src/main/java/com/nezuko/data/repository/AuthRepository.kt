package com.nezuko.data.repository

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
interface AuthRepository {
    val authState: StateFlow<com.nezuko.data.model.ResultModel<com.nezuko.data.model.AuthState>>
    val isFirstScreenLoaded: StateFlow<Boolean>

    suspend fun registerAccount(user: com.nezuko.data.model.UserRegister)
    suspend fun login(user: com.nezuko.data.model.UserRegister)
    fun leaveAccount()
    fun setFirstScreenLoaded()
}

