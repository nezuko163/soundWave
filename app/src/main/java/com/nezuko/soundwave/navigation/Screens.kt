package com.nezuko.soundwave.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.nezuko.auth.AuthViewModel
import com.nezuko.auth.LoginRoute
import com.nezuko.auth.RegisterRoute
import com.nezuko.auth.StartRoute
import com.nezuko.home.HomeRoute
import com.nezuko.home.HomeViewModel
import com.nezuko.library.LibraryRoute
import com.nezuko.library.LibraryViewModel
import com.nezuko.playlist.PlaylistRoute
import com.nezuko.search.SearchRoute
import com.nezuko.search.SearchViewModel

private const val TAG = "SCREENS_TAG"

abstract class ScreenWithBottomBar : Screen

private var a: UShort = 0u

object HomeScreen : Tab {
    private fun readResolve(): Any = HomeScreen

    override val options: TabOptions
        @Composable
        get() {
            val title = "Главная"
            val icon = rememberVectorPainter(Icons.Outlined.Home)

            return remember {
                TabOptions(
                    index = a++,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val vm: HomeViewModel = hiltViewModel()

        HomeRoute(vm = vm)
    }
}

object AuthScreen : Screen {
    private fun readResolve(): Any = AuthScreen

    @Composable
    override fun Content() {
        val vm: AuthViewModel = hiltViewModel()

        Navigator(StartScreen(vm = vm)) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}

class RegisterScreen(private val vm: AuthViewModel) : Screen {

    @Composable
    override fun Content() {
        RegisterRoute(vm = vm)
    }

}

class StartScreen(private val vm: AuthViewModel) : Screen {
    private fun readResolve(): Any = this

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        StartRoute(
            vm = vm,
            onSignUpClick = {
                navigator.push(RegisterScreen(vm))
            },
            onSignInClick = {
                navigator.push(LoginScreen(vm))
            }
        )
    }

}

class LoginScreen(private val vm: AuthViewModel) : Screen {

    @Composable
    override fun Content() {
        LoginRoute(vm = vm)
    }

}

object SearchScreen : Tab {
    private fun readResolve(): Any = SearchScreen

    override val options: TabOptions
        @Composable
        get() {
            val title = "Поиск"
            val icon = rememberVectorPainter(Icons.Outlined.Search)

            return remember {
                TabOptions(
                    index = a++,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val vm: SearchViewModel = hiltViewModel()

        SearchRoute(vm = vm)
    }
}

object LibraryScreen : Tab {
    private fun readResolve(): Any = LibraryScreen

    override val options: TabOptions
        @Composable
        get() {
            val title = "Библиотека"
            val icon = rememberVectorPainter(Icons.Default.Menu)

            return remember {
                TabOptions(
                    index = a++,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val vm: LibraryViewModel = hiltViewModel()

        Navigator(PlaylistsScreen(vm)) {
            CurrentScreen()
        }


    }

    class PlaylistsScreen(private val vm: LibraryViewModel) : Screen {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            LibraryRoute(
                vm = vm,
                onPlaylistClick = { playlist ->
                    navigator.push(PlaylistScreen(playlist.id))
                }
            )
        }

    }
}

class MainScreen : Screen {

    @Composable
    override fun Content() {
        Log.i(TAG, "Content: ")
        TabNavigator(tab = LibraryScreen) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        TabNavigationItem(HomeScreen)
                        TabNavigationItem(SearchScreen)
                        TabNavigationItem(LibraryScreen)
                    }
                },
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    CurrentTab()
                }
            }
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        Log.i(TAG, "TabNavigationItem: ${tabNavigator.current}")
        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }
}

class PlaceHolderScreen : Screen {

    @Composable
    override fun Content() {
        Box {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "SPLACH SCREEN",
                fontSize = 30.sp
            )
        }
    }
}

class PlaylistScreen(val playlistID: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        PlaylistRoute(
            id = playlistID,
            onNavigateBack = navigator::pop
        )
    }
}
