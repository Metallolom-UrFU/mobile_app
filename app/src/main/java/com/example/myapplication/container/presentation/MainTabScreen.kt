package com.example.myapplication.container.presentation

import MenuBar
import com.example.myapplication.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import com.example.myapplication.auth.presentation.screens.AuthScreen
import com.example.myapplication.bookList.presentation.screens.BookListScreen
import com.example.myapplication.shelfList.presentation.screens.ShelfListScreen
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

        //val showBottomBar = selectedScreen != AppTabs.AUTH.ordinal
        val showBottomBar = true
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

enum class AppTabs(@DrawableRes val iconRes: Int, val screen: Screen) {
    //AUTH(Icons.Filled.Person, AuthScreen()),
    POSTS(R.drawable.ic_shelf, ShelfListScreen()),
    BOOKS(R.drawable.ic_books, BookListScreen()),
    PROFILE(R.drawable.ic_person, ProfileScreen())
}