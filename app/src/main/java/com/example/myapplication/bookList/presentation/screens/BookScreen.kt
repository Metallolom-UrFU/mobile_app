package com.example.myapplication.bookList.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.bookList.presentation.viewModel.BookDetailsViewModel
import com.example.myapplication.ui.components.PostItemCard
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import com.example.myapplication.map.presentation.screen.MapScreen
import com.example.myapplication.ui.components.FullscreenLoading
import com.github.terrakok.modo.stack.forward


@Parcelize
class BookScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
    val bookId: String
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<BookDetailsViewModel> { parametersOf(navigation, bookId) }
        val state = viewModel.viewState

        Scaffold(
            topBar = { SimpleAppBar(state.book?.title.orEmpty()) { viewModel.back() } }
        ) {
            if (state.isLoading) {
                FullscreenLoading()
                return@Scaffold
            }

            val book = state.book
            if (book == null) {
                Text("Не удалось загрузить книгу", modifier = Modifier.padding(it))
                return@Scaffold
            }

            LazyColumn(
                modifier = Modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(Spacing.medium)
            ) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 4.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            AsyncImage(
                                model = book.image,
                                contentDescription = book.title,
                                modifier = Modifier
                                    .size(width = 96.dp, height = 128.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = book.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = book.author,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.medium))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        tonalElevation = 2.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                book.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.medium))


                    Text(
                        text = if (state.shelves.isEmpty()) "Нет в наличии"
                        else "В наличии в постаматах",
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }



                items(state.shelves) { (shelf, bookInstanceId) ->
                    PostItemCard(
                        item = shelf,
                        isBookScreen = true,
                        onClick = {
                            viewModel.onClickOrder(
                                bookInstanceId = bookInstanceId,
                                userId = "0764dd56-755e-4846-85ff-3a643fde4327"
                            )
                        }
                    )
                }

                item {
                    if (state.shelves.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Посмотреть на карте",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        navigation.forward(
                                            MapScreen(
                                                shelfBookPairs = state.shelves.map { (shelf, bookInstanceId) ->
                                                    shelf.id to bookInstanceId
                                                }
                                            )
                                        )
                                    }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}
