package com.nezuko.auth

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.model.UserRegister
import com.nezuko.data.repository.AuthRepository
import com.nezuko.data.repository.PermissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val permissionRepository: PermissionRepository
) : ViewModel() {

    val authState = authRepository.authState

    fun authComplete() {
        viewModelScope.launch {
            authRepository.registerAccount(UserRegister("", "", ""))
        }
    }

    fun setActivity(activity: Activity) {
        permissionRepository.setActivity1(activity)
    }

    private val notificationPermission = permissionRepository.notificationPermissionIsGranted

    fun checkNotificationPermission() {
        permissionRepository.checkPermissions()
        Log.i(TAG, "checkNotificationPermission: ${notificationPermission.value}")
        if (!notificationPermission.value) {
            Log.i(TAG, "checkNotificationPermission: asd")
            permissionRepository.requestNotificationPermission()
        }
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}