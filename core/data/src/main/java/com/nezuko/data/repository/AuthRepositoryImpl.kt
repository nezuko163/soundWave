package com.nezuko.data.repository

import android.util.Log
import com.nezuko.data.model.AuthState
import com.nezuko.data.model.ResultModel
import com.nezuko.data.model.UserRegister
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val TAG = "AUTH REPOSITORY IMPL"
    private val _authState = MutableStateFlow<ResultModel<AuthState>>(ResultModel.none())

    override val authState: StateFlow<ResultModel<AuthState>>
        get() = _authState

    private val _isFirstScreenLoaded = MutableStateFlow(false)

    override val isFirstScreenLoaded: StateFlow<Boolean>
        get() = _isFirstScreenLoaded

    override suspend fun registerAccount(user: UserRegister) {
        Log.i(TAG, "registerAccount: $user")
        if (_authState.value.data != AuthState.Ready) {
            _authState.update { ResultModel.success(AuthState.Ready) }
        }
    }

    override suspend fun login(user: UserRegister) {
        Log.i(TAG, "login: 1")
    }

    override fun leaveAccount() {
        if (_authState.value.data != AuthState.Unregister) {
            _authState.update { ResultModel.success(AuthState.Unregister) }
        }
    }

    override fun setFirstScreenLoaded() {
        _isFirstScreenLoaded.update { true }
    }
}