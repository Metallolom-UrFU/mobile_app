package com.example.myapplication.shelfList.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.coroutinesUtils.launchLoadingAndError
import com.example.myapplication.shelfList.domain.entity.ShelfFullEntity
import com.example.myapplication.shelfList.domain.repository.IShelfRepository
import com.example.myapplication.shelfList.presentation.state.PostDetailState
import com.example.myapplication.profile.domain.repository.IReservationRepository
import com.github.terrakok.modo.stack.StackNavContainer
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.launch

class ShelfDetailsViewModel(
    private val navigation: StackNavContainer,
    private val repository: IShelfRepository,
    private val reservationRepository: IReservationRepository,
    private val shelfId: String
) : ViewModel() {
    private val mutableState = MutablePostDetailsState()

    val viewState = mutableState as PostDetailState

    init {
        viewModelScope.launch {
            loadShelf()
        }
    }


    private fun loadShelf() {
        mutableState.shelf = null
        mutableState.error = null

        viewModelScope.launchLoadingAndError(
            handleError = { mutableState.error = it.localizedMessage },
            updateLoading = { mutableState.isLoading = it }

        ) {
            mutableState.shelf = repository.getById(shelfId)
        }
    }

    fun back() {
        navigation.back()
    }

    fun onClickOrder(userId: String, bookInstanceId: String) {
        viewModelScope.launch {
            reservationRepository.createReservation(bookInstanceId,  userId)
            loadShelf()
        }
    }


    private class MutablePostDetailsState : PostDetailState {
        override var shelf: ShelfFullEntity? by mutableStateOf(null)

        override var isLoading: Boolean by mutableStateOf(false)
        override var error: String? by mutableStateOf(null)
    }
}