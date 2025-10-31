package com.example.myapplication.bookList.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.bookList.presentation.state.BookDetailState
import com.example.myapplication.bookList.presentation.viewModel.DetailsViewModel
import com.example.myapplication.ui.components.SimpleAppBar
import com.example.myapplication.ui.theme.Spacing
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class BookScreen(
    override val screenKey: ScreenKey = generateScreenKey(), val bookId: String
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<DetailsViewModel> { parametersOf(navigation, bookId) }
        val state = viewModel.viewState

        BookScreenContent(
            state,
            onBackPressed = { viewModel.back() }
        )
    }
}

@Composable
private fun BookScreenContent(
    state: BookDetailState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = { SimpleAppBar(state.book?.title.orEmpty(), onBackPressed) },
    ) {
//        if (state.isLoading) {
//            FullscreenLoading()
//            return@Scaffold
//        }
//
//        val book = state.book ?: return@Scaffold

        Column(
            Modifier
                .padding(it)
                .padding(Spacing.medium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.medium)) {
//                AsyncImage(
//                    model = book.image,
//                    contentDescription = null,
//                    modifier = Modifier.size(100.dp),
//                    contentScale = ContentScale.Crop,
//                )

                Column {
                    Text(
                        text = book.title, style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(Spacing.medium))

                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(Spacing.medium))

        Text(
            text = book.description,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(Spacing.medium))

        Row {
            Text("В наличии в постаматах")
        }

        LazyColumn {
            items(book.posts)
            {
                Text("${it.name}, ${it.address}")
            }
        }
        Box(
            modifier = Modifier.padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Посмотреть на карте",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xAA000000)
            )
        }
    }
}

