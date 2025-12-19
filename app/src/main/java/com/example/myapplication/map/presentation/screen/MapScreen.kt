package com.example.myapplication.map.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.map.presentation.ViewModel.MapViewModel
import com.example.myapplication.ui.components.ShelfBottomCard
import com.example.myapplication.ui.components.SimpleAppBar
import com.example.myapplication.ui.components.YandexMapComposable
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class MapScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
    private val shelfBookPairs: List<Pair<String, String>>
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<MapViewModel> { parametersOf(navigation, shelfBookPairs) }
        val state = viewModel.viewState

        Scaffold(
            topBar = { SimpleAppBar("Полки с этой книгой") { viewModel.back() } },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                YandexMapComposable(
                    shelves = state.shelves.map { it.first },
                    onShelfClick = viewModel::onShelfClick,
                    modifier = Modifier.fillMaxSize()
                )

                state.selectedShelf?.let { (shelf, _) ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp)
                    ) {
                        ShelfBottomCard(
                            item = shelf,
                            onOrderClick = { viewModel.orderBook("0764dd56-755e-4846-85ff-3a643fde4327") },
                            onClose = viewModel::clearSelection
                        )
                    }
                }
            }
        }
    }
}