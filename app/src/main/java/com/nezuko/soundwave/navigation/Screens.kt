package com.nezuko.soundwave.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.nezuko.addnewplaylist.AddNewPlaylistRoute
import com.nezuko.addnewtrackstoplaylist.AddNewTracksToPlaylistRoute
import com.nezuko.addnewtrackstoplaylist.AddNewTracksToPlaylistViewModel
import com.nezuko.auth.AuthViewModel
import com.nezuko.auth.LoginRoute
import com.nezuko.auth.RegisterRoute
import com.nezuko.auth.StartRoute
import com.nezuko.data.model.Playlist
import com.nezuko.home.HomeRoute
import com.nezuko.home.HomeViewModel
import com.nezuko.library.LibraryRoute
import com.nezuko.playlist.PlaylistDetailsRoute
import com.nezuko.search.SearchRoute
import com.nezuko.search.SearchViewModel

private const val TAG = "SCREENS_TAG"

object HomeScreen : Tab {
    private fun readResolve(): Any = HomeScreen

    override val options: TabOptions
        @Composable
        get() {
            val title = "Главная"
            val icon = rememberVectorPainter(Icons.Outlined.Home)

            return remember {
                TabOptions(
                    index = 1u,
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
                    index = 2u,
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
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(PlaylistsScreen()) {
            CurrentScreen()
        }
    }

    class PlaylistsScreen : Screen {
        @Composable
        override fun Content() {
            Log.i(TAG, "Content: playlists")
            val navigator = LocalNavigator.currentOrThrow
            LibraryRoute(
                onPlaylistDetailsNavigate = { playlist ->
                    navigator.push(PlaylistDetailsScreen(playlist.id))
                },
                onAddNewPlaylistNavigate = { playlist: Playlist ->
                    val id = if (playlist.id != 0L) playlist.id else null
                    navigator.push(AddNewPlaylistScreen(id))
                }
            )
        }

    }
}

class MainScreen : Screen {

    @Composable
    override fun Content() {
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

class PlaylistDetailsScreen(private val playlistID: Long) : Screen {
    @Composable
    override fun Content() {
        Log.i(TAG, "Content: playlist details")
        val navigator = LocalNavigator.currentOrThrow
        PlaylistDetailsRoute(
            id = playlistID,
            onNavigateBack = navigator::pop
        )
    }
}


class AddNewPlaylistScreen(
    private val id: Long? = null
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AddNewPlaylistRoute(
            id = id,
            onNavigateBack = navigator::pop,
            onDone = navigator::pop,
            onAddNewTracksNavigate = { playlist ->
                navigator.push(AddNewTracksToPlaylistScreen(id))
            }
        )
    }
}

class AddNewTracksToPlaylistScreen(private val id: Long? = null) : Screen {
    @Composable
    override fun Content() {
        val vm: AddNewTracksToPlaylistViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        AddNewTracksToPlaylistRoute(id = id, vm = vm, onNavigateBack = navigator::pop)
    }
}