package com.example.myapplication.shelfList.presentation.screens

import com.example.myapplication.shelfList.presentation.viewModel.ShelfDetailsViewModel
import com.example.myapplication.ui.components.BookItemCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.SimpleAppBar
import com.example.myapplication.ui.theme.Spacing
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.foundation.lazy.items
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import com.example.myapplication.profile.presentation.state.ReservationUiStatus
import com.example.myapplication.ui.components.FullscreenLoading


@Parcelize
class ShelfScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
    val postId: String
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<ShelfDetailsViewModel> { parametersOf(navigation, postId) }
        val state = viewModel.viewState

        Scaffold(
            topBar = { SimpleAppBar(state.shelf?.name.orEmpty()) { viewModel.back() } },
        ) { paddingValues ->

            if (state.isLoading) {
                FullscreenLoading()
                return@Scaffold
            }
            val shelf = state.shelf ?: return@Scaffold

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                item {
                    Text(
                        text = "Статус: ${shelf.status}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = Spacing.medium)
                    )

                    Spacer(modifier = Modifier.height(Spacing.medium))

                    Text(
                        text = "Книги",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = Spacing.medium)
                    )

                    Spacer(modifier = Modifier.height(Spacing.small))
                }

                items(shelf.books) { item ->

                    BookItemCard(
                        item = BookShortEntity(
                            title = item.title,
                            author = item.author,
                            tags = item.tags,
                            imageRes = item.image,
                            id = item.id
                        ),
                        reservationStatus = ReservationUiStatus.AVAILABLE,
                        canOpenDetails = false,
                        onClickReserve = {
                            viewModel.onClickOrder(
                                "0764dd56-755e-4846-85ff-3a643fde4327",
                                item.availableInstance
                            )
                        },
                    )
                }
            }
        }
    }
}


