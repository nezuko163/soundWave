package com.nezuko.data.repository

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nezuko.data.REQUEST_CODE_AUDIO
import com.nezuko.data.REQUEST_CODE_NOTIFICATION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionRepositoryImpl @Inject constructor() : PermissionRepository {

    private val TAG = "PermissionRepositoryImpl"
    lateinit var myActivity: Activity

    private val _audioPermissionIsGranted = MutableStateFlow<Boolean>(false)
    override val audioPermissionIsGranted: StateFlow<Boolean>
        get() = _audioPermissionIsGranted

    private val _notificationPermissionIsGranted = MutableStateFlow<Boolean>(false)
    override val notificationPermissionIsGranted: StateFlow<Boolean>
        get() = _notificationPermissionIsGranted

    override fun setActivity1(activity: Activity) {
        this.myActivity = activity
    }

    override fun checkPermissions() {
        checkAudioPermission()
        checkNotificationPermission()
    }

    private fun checkAudioPermission() {
        _audioPermissionIsGranted.update {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    myActivity,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(
                    myActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        Log.i(TAG, "checkAudioPermission: ${_audioPermissionIsGranted.value}")
    }

    private fun checkNotificationPermission() {
        _notificationPermissionIsGranted.update {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    myActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        }
    }

    private fun requestPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                myActivity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "requestPermission: $permission")
            ActivityCompat.requestPermissions(myActivity, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, grantResult: Boolean) {

        Log.i(TAG, "onRequestPermissionsResult: $requestCode")
        when (requestCode) {
            REQUEST_CODE_NOTIFICATION -> {
                if (_notificationPermissionIsGranted.value != grantResult) {
                    _notificationPermissionIsGranted.update {
                        Log.i(TAG, "onRequestPermissionsResult: notific - $grantResult")
                        grantResult
                    }
                }
            }

            REQUEST_CODE_AUDIO -> {
                if (_audioPermissionIsGranted.value != grantResult) {
                    _audioPermissionIsGranted.update {
                        Log.i(TAG, "onRequestPermissionsResult: audio - $grantResult")
                        grantResult
                    }
                }
            }
        }
    }
}