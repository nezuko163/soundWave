package com.nezuko.soundwave

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.nezuko.soundwave.navigation.PlaceHolderScreen

private val TAG = "MAIN_APP"

@Composable
fun App(
    modifier: Modifier = Modifier,
    startDestination: Screen = PlaceHolderScreen()
) {
    Log.i(TAG, "App: $startDestination")
    Navigator(screen = startDestination) { navigator ->
        if (navigator.lastItem != startDestination) navigator.replace(startDestination)
        CurrentScreen()
    }
}

