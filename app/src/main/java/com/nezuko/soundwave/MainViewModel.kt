package com.nezuko.soundwave

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authState = authRepository.authState

    val isFirstScreenLoaded = authRepository.isFirstScreenLoaded

    init {
        Log.i(TAG, "MainViewModel init: ${authState.hashCode()}")
        viewModelScope.launch {
            delay(500)
            authRepository.setFirstScreenLoaded()
        }
    }

    fun unlogin() {
        authRepository.leaveAccount()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}