package com.nezuko.data.repository

import android.app.Activity
import kotlinx.coroutines.flow.StateFlow


interface PermissionRepository {
    val audioPermissionIsGranted: StateFlow<Boolean>
    val notificationPermissionIsGranted: StateFlow<Boolean>

    fun setActivity1(activity: Activity)
    fun checkPermissions()
    fun requestNotificationPermission()
    fun requestAudioPermission()
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
}