package com.example.myapplication.shelfList.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.shelfList.presentation.viewModel.PostListViewModel
import com.example.myapplication.ui.components.PostItemCard
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.foundation.lazy.items


@Parcelize
class ShelfListScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<PostListViewModel> { parametersOf(navigation) }
        val state = viewModel.viewState

        Scaffold(
            topBar = {

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text("Список постаматов")
                    Box(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "На карте",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xAA000000)
                        )
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.shelves) {
                    PostItemCard(item = it, onClick = { viewModel.onItemClicked(it.id) })
                }
            }
        }
    }
}