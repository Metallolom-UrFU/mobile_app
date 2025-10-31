package com.example.myapplication.postList.presentation.screens

import com.example.myapplication.postList.presentation.viewModel.PostDetailsViewModel
import com.example.myapplication.ui.components.BookItemCard

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
import com.example.myapplication.postList.presentation.state.PostDetailState
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
class PostScreen(
    override val screenKey: ScreenKey = generateScreenKey(), val postId: String
) : Screen {
    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<PostDetailsViewModel> { parametersOf(navigation, postId) }
        val state = viewModel.viewState

        PostScreenContent(
            state,
            onBackPressed = { viewModel.back() }
        )
    }
}

@Composable
private fun PostScreenContent(
    state: PostDetailState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = { SimpleAppBar(state.post?.title.orEmpty(), onBackPressed) },
    ) {
//        if (state.isLoading) {
//            FullscreenLoading()
//            return@Scaffold
//        }
//
//        val post = state.post ?: return@Scaffold

        Column(
            Modifier
                .padding(it)
                .padding(Spacing.medium)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.medium)) {

                Column {
                    Text(
                        text = "Адрес: ${post.address}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Статус: ${post.status}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(Spacing.medium))

        Text(
            text = "Книги",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(Spacing.small))

        LazyColumn {
            items(post.books)
            {
                BookItemCard(
                    it,
                    canOrder = true,
                )
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

