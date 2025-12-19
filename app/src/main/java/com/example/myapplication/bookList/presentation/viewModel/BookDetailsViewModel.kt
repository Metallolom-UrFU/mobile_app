package com.example.myapplication.bookList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bookList.domain.entity.BookEntity
import com.example.myapplication.bookList.domain.repository.IBookRepository
import com.example.myapplication.bookList.presentation.state.BookDetailState
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import com.example.myapplication.profile.domain.repository.IReservationRepository
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val repository: IBookRepository,
    private val reservationRepository: IReservationRepository,
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
            mutableState.book = repository.getById(id)
            mutableState.shelves = repository.getShelvesByBook(id)
        }
    }

    fun back() {
        navigation.back()
    }

    fun onClickOrder(userId: String, bookInstanceId: String) {
        viewModelScope.launch {
            reservationRepository.createReservation(bookInstanceId, userId)
        }
        back()
    }


    private class MutableDetailsState : BookDetailState {
        override var book: BookEntity? by mutableStateOf(null)
        override var shelves: List<Pair<ShelfShortEntity, String>> by mutableStateOf(emptyList())
        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }
}