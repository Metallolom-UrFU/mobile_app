package com.example.myapplication.container.presentation

import MenuBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapplication.auth.presentation.screens.AuthScreen
import com.example.myapplication.bookList.presentation.screens.BookListScreen
import com.example.myapplication.postList.presentation.screens.PostListScreen
import com.example.myapplication.profile.presentation.screens.ProfileScreen
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.animation.SlideTransition
import com.github.terrakok.modo.multiscreen.MultiScreen
import com.github.terrakok.modo.multiscreen.MultiScreenNavModel
import com.github.terrakok.modo.multiscreen.selectScreen
import kotlinx.parcelize.Parcelize

@Parcelize
class MainTabScreen(
    private val navModel: MultiScreenNavModel = MultiScreenNavModel(
        screens = AppTabs.entries.map { it.screen }, selected = 0
    )
) : MultiScreen(navModel) {

    @Composable
    override fun Content(modifier: Modifier) {
        val selectedScreen = navigationState.selected

        val showBottomBar = selectedScreen != AppTabs.AUTH.ordinal

        Scaffold(
            modifier = modifier,
            bottomBar = {
                if (showBottomBar) {
                    MenuBar(navigationState.selected) { pos -> selectScreen(pos) }
                }
            }
        ) { paddingValues ->
            SelectedScreen(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) { innerModifier ->
                SlideTransition(innerModifier)
            }
        }
    }
}

enum class AppTabs(val icon: ImageVector, val screen: Screen) {
    AUTH(Icons.Filled.Person, AuthScreen()),
    POSTS(Icons.Filled.Star, PostListScreen()),
    BOOKS(Icons.AutoMirrored.Rounded.List, BookListScreen()),
    PROFILE(Icons.Filled.Person, ProfileScreen())
}