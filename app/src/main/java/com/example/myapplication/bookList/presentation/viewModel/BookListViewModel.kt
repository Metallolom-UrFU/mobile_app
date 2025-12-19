package com.example.myapplication.bookList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import com.example.myapplication.bookList.presentation.screens.BookScreen
import com.example.myapplication.bookList.presentation.state.BooksListState
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.forward
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import org.koin.java.KoinJavaComponent.inject
import java.time.Duration
import kotlin.jvm.java
import androidx.datastore.preferences.core.Preferences
import com.example.myapplication.bookList.domain.repository.IBookRepository


@OptIn(FlowPreview::class)
class BookListViewModel(
    private val repository: IBookRepository,
    private val navigation: StackNavContainer
) : ViewModel() {
    private val mutableState = MutableBooksListState()
    private val textChangesFlow = MutableStateFlow("")
    private val dataStore: DataStore<Preferences> by inject(DataStore::class.java)
    private val typesKey = stringSetPreferencesKey("BOOK_GENRES")
    private var filterTypes: Set<String> = emptySet()


    val viewState = mutableState as BooksListState

    init {
        mutableState.typesVariants = setOf(
            "Классика", "Приключения"
        )

        viewModelScope.launch {
            textChangesFlow.debounce(Duration.ofSeconds(1L)).collect { loadBooks(it) }
        }
        viewModelScope.launch {
            dataStore.data.collect { data ->
                filterTypes = data[typesKey]?.toSet() ?: emptySet()
                updateBadge()
            }
        }
    }

    private fun loadBooks(query: String) {
        mutableState.items = emptyList()
        mutableState.isEmpty = true
        mutableState.error = null

        viewModelScope.launchLoadingAndError(
            handleError = {
                mutableState.error = it.localizedMessage
            },
            updateLoading = { mutableState.isLoading = it }

        ) {
            mutableState.items = repository.getList(query, "", filterTypes.toList())
            mutableState.isEmpty = mutableState.items.isEmpty()
        }
    }

    fun onQueryChanged(query: String) {
        mutableState.query = query
        viewModelScope.launch { textChangesFlow.emit(query) }
    }

    fun onItemClicked(id: String) {
        navigation.forward(BookScreen(bookId = id))
    }

    fun onFiltersConfirmed() {
        if (filterTypes != mutableState.selectedTypes) {
            filterTypes = mutableState.selectedTypes
            loadBooks(textChangesFlow.value)
            updateBadge()
            viewModelScope.launch {
                dataStore.edit {
                    if (filterTypes.isEmpty()) it.remove(typesKey)
                    else it[typesKey] = filterTypes
                }
            }
        }
        onSelectionDialogDismissed()

    }

    fun onFiltersClicked() {
        mutableState.selectedTypes = filterTypes
        mutableState.showTypesDialog = true
    }

    fun onFiltersCanceled() {
        mutableState.showTypesDialog = false
    }

    fun onFiltersChanged(genre: String, selected: Boolean) {
        mutableState.selectedTypes = mutableState.selectedTypes.run {
            if (selected) plus(genre) else minus(genre)
        }
    }

    private fun updateBadge() {
        mutableState.hasBadge = filterTypes.isNotEmpty()
    }

    private fun onSelectionDialogDismissed() {
        mutableState.showTypesDialog = false
    }

    private class MutableBooksListState(
    ) : BooksListState {
        override var query by mutableStateOf(String())

        override var items: List<BookShortEntity> by mutableStateOf(emptyList())
        override var isEmpty: Boolean by mutableStateOf(true)
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
        override var showTypesDialog: Boolean by mutableStateOf(false)
        override var hasBadge: Boolean by mutableStateOf(false)
        override var typesVariants: Set<String> by mutableStateOf(emptySet())
        override var selectedTypes: Set<String> by mutableStateOf(emptySet())
    }
}
