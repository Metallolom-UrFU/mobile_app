package com.example.myapplication.bookList.presentation.screens

import com.example.myapplication.R
import com.example.myapplication.bookList.presentation.viewModel.BookListViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.BadgedIcon
import com.example.myapplication.ui.components.BookItemCard
import com.example.myapplication.ui.components.FullscreenLoading
import com.example.myapplication.ui.components.FullscreenMessage
import com.example.myapplication.ui.components.SearchBar
import com.example.myapplication.ui.components.SelectionDialog
import com.example.myapplication.ui.theme.Spacing
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class BookListScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<BookListViewModel> { parametersOf(navigation) }
        val state = viewModel.viewState

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier.padding(Spacing.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBar(state.query) { query -> viewModel.onQueryChanged(query) }
                    BadgedIcon(state.hasBadge) { viewModel.onFiltersClicked() }
                }
            }
        ) { paddingValues ->
            if (state.showTypesDialog) {
                SelectionDialog(
                    title = "Жанр",
                    variants = state.typesVariants,
                    selectedVariants = state.selectedTypes,
                    onDismissRequest = { viewModel.onFiltersCanceled() },
                    onVariantSelectedChanged = { variant, isSelected ->
                        viewModel.onFiltersChanged(variant, isSelected)
                    },
                    onConfirmation = { viewModel.onFiltersConfirmed() }
                )
            }

            if (state.isLoading) {
                FullscreenLoading()
                return@Scaffold
            }
            state.error?.let {
                FullscreenMessage(msg = it)
                return@Scaffold
            }

            if (state.items.isEmpty()) {
                FullscreenMessage(stringResource(R.string.not_found))
                return@Scaffold
            }

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.items) {
                    BookItemCard(
                        item = it,
                        canOpenDetails = true,
                        onClickOpenBook = { viewModel.onItemClicked(it.id) }
                    )
                }
            }
        }
    }
}