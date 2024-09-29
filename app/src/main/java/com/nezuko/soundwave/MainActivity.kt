package com.nezuko.soundwave

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.core.screen.Screen
import com.nezuko.data.model.AuthState
import com.nezuko.soundwave.navigation.AuthScreen
import com.nezuko.soundwave.navigation.MainScreen
import com.nezuko.ui.theme.SoundWaveTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MAIN_ACTIVITY"


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val splashScreen = installSplashScreen()

            val uiState by vm.authState.collectAsState()
            Log.i(TAG, "onCreate: ${uiState.data}")
            val isFirstScreenLoaded by vm.isFirstScreenLoaded.collectAsState(false)
            splashScreen.setKeepOnScreenCondition { !isFirstScreenLoaded }

            val startDestination: Screen = when (uiState.data) {
                AuthState.Ready -> MainScreen
                AuthState.WaitRegister, AuthState.Unregister, null -> AuthScreen
            }
            SoundWaveTheme {
                Log.i(TAG, "onCreate: $startDestination")
                App(startDestination = startDestination)
            }
        }
    }
}