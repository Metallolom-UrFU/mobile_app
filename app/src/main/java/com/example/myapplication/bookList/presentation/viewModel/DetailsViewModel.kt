package com.example.myapplication.bookList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bookList.presentation.state.BookDetailState
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back

class DetailsViewModel(
    private val navigation: StackNavContainer,
    private val id: String
) : ViewModel() {
    private val mutableState = MutableDetailsState()
    val viewState = mutableState as BookDetailState

    init {
        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }
        ) {
//            mutableState.post = repository.getById(id)
        }
    }

    fun back() {
        navigation.back()
    }


    private class MutableDetailsState : BookDetailState {
//        override var post: bookEntity? by mutableStateOf(null)

        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }
}